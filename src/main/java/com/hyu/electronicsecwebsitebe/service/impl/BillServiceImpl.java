package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.dto.request.ReturnItem;
import com.hyu.electronicsecwebsitebe.model.*;
import com.hyu.electronicsecwebsitebe.repository.*;
import com.hyu.electronicsecwebsitebe.service.BillService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private DetailBillRepository detailBillRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private GhnLocationServiceImpl ghnLocationService;

    @Override
    public List<Bill> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return bills;
    }

    @Override
    public List<Bill> findByCustomerId(String customerId){
        return  billRepository.findByCustomerId(customerId);
    }

    public List<Bill> getBillsByEmployee(String employeeId) {
        return billRepository.findByEmployeeId(employeeId);
    }

    @Transactional
    public Bill updateBillStatus(String billId, String status, String employeeId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
        if ("Đang giao".equals(status)) {
            if (!"Đơn đang chờ giao".equals(bill.getStatus())) {
                throw new RuntimeException("Đơn đã được nhận bởi shipper khác");
            }
        }
        if (employeeId != null) {
            Employee employee = employeeService.findById(employeeId);
            bill.setEmployee(employee);
        }
        if("Đã giao".equalsIgnoreCase(status)){
            bill.setDeliveryDate(new Date());
        }
        bill.setStatus(status);
        return billRepository.save(bill);
    }

    @Transactional
    public Bill createbillfromcart(Customer customer, Employee employee, Address address, String paymentMethod) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByCustomerId(customer.getId());

        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống, không thể tạo hóa đơn");
        }
        BigDecimal totalBigDecimal = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int totalAmount = totalBigDecimal.intValue();
        System.out.printf("Cac san pham trong gio hang: " + cartItems.size());

        // Tạo hóa đơn mới
        Bill bill = new Bill();
        bill.setId(generateBillId());
        bill.setDate(new Date());

        // Set phương thức thanh toán
        bill.setPaymentMethod(paymentMethod);

        // Set trạng thái dựa trên phương thức thanh toán
        if ("Chuyển khoản ngân hàng".equals(paymentMethod)) {
            bill.setStatus("Đơn chưa thanh toán");
        } else {
            bill.setStatus("Chờ xác nhận");
        }

        bill.setCustomer(customer);
        bill.setEmployee(employee);
        bill.setAddress(address);
        BigDecimal shippingFee = BigDecimal.valueOf(ghnLocationService.calculateShippingFee(address, totalAmount));
        bill.setShippingFee(shippingFee);

        // Lưu hóa đơn
        Bill savedBill = billRepository.save(bill);

        // Tạo chi tiết hóa đơn từ giỏ hàng
        for (ShoppingCart cartItem : cartItems) {
            DetailBill detailBill = new DetailBill();
            detailBill.setId(generateDetailBillId());
            detailBill.setBill(savedBill);
            detailBill.setProduct(cartItem.getProduct());
            detailBill.setPrice(cartItem.getProduct().getDiscountedPrice());
            detailBill.setQuantity(cartItem.getQuantity());
            detailBill.setTotal(
                    cartItem.getProduct().getDiscountedPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );

            // Lưu chi tiết hóa đơn
            detailBillRepository.save(detailBill);

            // Cập nhật số lượng tồn kho
            Product product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        // Xóa giỏ hàng sau khi đã tạo hóa
        shoppingCartRepository.deleteAll(cartItems);
        //Debug
//        System.out.printf("Đã xóa thành công\n");

        return savedBill;
    }

    @Transactional
    @Override
    public Bill requestReturnBill(String billId, String reason, List<ReturnItem> returnItems) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với ID: " + billId));


        if (bill.getReturnDate() != null || "Yêu cầu trả hàng".equalsIgnoreCase(bill.getStatus()) || "Đã trả hàng".equalsIgnoreCase(bill.getStatus()) || "Từ chối trả hàng".equalsIgnoreCase(bill.getStatus())) {
            throw new RuntimeException("Đơn hàng này đã được yêu cầu trả hàng trước đó. Mỗi đơn chỉ được hỗ trợ trả 1 lần!");
        }

        if (!"Đã giao".equalsIgnoreCase(bill.getStatus())) {
            throw new RuntimeException("Chỉ có thể yêu cầu trả hàng cho đơn hàng đã hoàn tất!");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(bill.getDeliveryDate());
        cal.add(Calendar.DAY_OF_MONTH, 1); // Cộng thêm 1 ngày vào thời gian gốc

        Date deadline = cal.getTime(); // Hạn chót được phép trả hàng
        Date now = new Date();

        if (now.after(deadline)) {
            throw new RuntimeException("Đã quá thời hạn 1 ngày. Bạn không thể yêu cầu trả hàng cho đơn này nữa!");
        }

        // Cập nhật thông tin chung của Bill
        bill.setStatus("Yêu cầu trả hàng");
        bill.setReturnReason(reason);
        bill.setReturnDate(new Date());

        // Xử lý từng món hàng khách muốn trả
        for (ReturnItem item : returnItems) {
            DetailBill detail = detailBillRepository.findById(item.getDetailBillId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết hóa đơn"));

            // Kiểm tra bảo mật: Chi tiết này có đúng thuộc về Bill này không?
            if (!detail.getBill().getId().equals(bill.getId())) {
                throw new RuntimeException("Sản phẩm không thuộc hóa đơn này!");
            }

            // Kiểm tra số lượng: Trả > 0 và <= số đã mua
            if (item.getReturnQuantity() <= 0 || item.getReturnQuantity() > detail.getQuantity()) {
                throw new RuntimeException("Số lượng trả không hợp lệ đối với sản phẩm: " + detail.getProduct().getName());
            }

            // Cập nhật số lượng trả và tính tiền hoàn cho dòng này
            detail.setReturnedQuantity(item.getReturnQuantity());

            // Lấy giá lúc mua (price) nhân với số lượng trả
            BigDecimal refundForThisItem = detail.getPrice().multiply(BigDecimal.valueOf(item.getReturnQuantity()));
            detail.setTotalRefund(refundForThisItem);

            // Lưu lại thay đổi của DetailBill
            detailBillRepository.save(detail);
        }

        return billRepository.save(bill);
    }

    @Transactional
    @Override
    public Bill approveReturnBill(String billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        if (!"Yêu cầu trả hàng".equalsIgnoreCase(bill.getStatus())) {
            throw new RuntimeException("Hóa đơn này không có yêu cầu trả hàng hợp lệ!");
        }

        // Vòng lặp hoàn lại Tồn kho (Stock) dựa trên số lượng trả (returnedQuantity)
        List<DetailBill> detailBills = bill.getDetailBills();
        for (DetailBill detail : detailBills) {
            if (detail.getReturnedQuantity() > 0) {
                Product product = detail.getProduct();

                // CHỈ cộng lại số lượng khách thực tế trả (returnedQuantity)
                int newStock = product.getStock() + detail.getReturnedQuantity();
                product.setStock(newStock);

                productRepository.save(product);
            }
        }

        bill.setStatus("Đã trả hàng");

        return billRepository.save(bill);
    }

    /**
     * Nhân viên TỪ CHỐI yêu cầu trả hàng
     */
    @Transactional
    @Override
    public Bill rejectReturnBill(String billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        if (!"Yêu cầu trả hàng".equalsIgnoreCase(bill.getStatus())) {
            throw new RuntimeException("Hóa đơn này không hợp lệ để từ chối!");
        }

        bill.setStatus("Từ chối trả hàng");

        for (DetailBill detail : bill.getDetailBills()) {
            if (detail.getReturnedQuantity() > 0) {
                detail.setReturnedQuantity(0);
                detail.setTotalRefund(null);
                detailBillRepository.save(detail);
            }
        }

        return billRepository.save(bill);
    }

    private String generateBillId() {
        return "HD" + String.valueOf((countBillEntities()+1));
    }
    private int countBillEntities() {
        return (int) billRepository.count();
    }
//    private String generateDetailBillId() {
//        return "CT" + String.valueOf((countDetailBillEntities())+1);
//    }
//    private int countDetailBillEntities() {
//        return (int) billRepository.count();
//    }
    private String generateDetailBillId() {
        return "CT" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
