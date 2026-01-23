package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.*;
import com.hyu.electronicsecwebsitebe.repository.*;
import com.hyu.electronicsecwebsitebe.service.BillService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private CustomerRepository customerRepository;

    @Override
    public List<Bill> findByCustomerId(String customerId){
        return billRepository.findByCustomerId(customerId);
    }


    @Transactional
    public Bill createbillfromcart(Customer customer, Employee employee, Address address, String paymentMethod) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByCustomerId(customer.getId());

        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống, không thể tạo hóa đơn");
        }

        // Tạo hóa đơn mới
        Bill bill = new Bill();
        bill.setId(generateBillId());
        bill.setDate(new Date());

        // Set phương thức thanh toán
        bill.setPaymentMethod(paymentMethod);

        // Set trạng thái dựa trên phương thức thanh toán
        if ("Chuyển khoản ngân hàng".equals(paymentMethod)) {
            bill.setStatus("Chưa thanh toán");
        } else {
            bill.setStatus("Chờ xác nhận");
        }

        bill.setCustomer(customer);
        bill.setEmployee(employee);
        bill.setAddress(address);

        // Lưu hóa đơn
        Bill savedBill = billRepository.save(bill);

        // Tạo chi tiết hóa đơn từ giỏ hàng
        for (ShoppingCart cartItem : cartItems) {
            DetailBill detailBill = new DetailBill();
            detailBill.setId(generateDetailBillId());
            detailBill.setBill(savedBill);
            detailBill.setProduct(cartItem.getProduct());
            detailBill.setQuantity(cartItem.getQuantity());
            detailBill.setTotal(
                    cartItem.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );

            // Lưu chi tiết hóa đơn
            detailBillRepository.save(detailBill);

            // Cập nhật số lượng tồn kho
            Product product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        // Xóa giỏ hàng sau khi đã tạo hóa đơn
//        shoppingCartRepository.deleteById(customer.getId());

        return savedBill;
    }

    private String generateBillId() {
        return "HD" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
    private String generateDetailBillId() {
        return "CT" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
