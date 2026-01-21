package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.*;
import com.hyu.electronicsecwebsitebe.repository.BillRepository;
import com.hyu.electronicsecwebsitebe.repository.CustomerRepository;
import com.hyu.electronicsecwebsitebe.repository.DetailBillRepository;
import com.hyu.electronicsecwebsitebe.service.BillService;
import com.hyu.electronicsecwebsitebe.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private DetailBillRepository detailBillRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<Bill> findByCustomerId(String customerId) {
        return billRepository.findByCustomerId(customerId);
    }

    @Transactional
    public Bill createbillfromcart(Customer customer, Employee employee, Address address, String paymentMethod) {
        ShoppingCart cartItems = ShoppingCartServiceImpl.findByCustomerId(customer.getId());

        if (cartItems == null) {
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
            detailBill.setTotal(cartItem.getProduct().getPrice() * cartItem.getQuantity());

            // Lưu chi tiết hóa đơn
            detailBillRepository.save(detailBill);

            // Cập nhật số lượng tồn kho
            Product product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productService.save(product);
        }

        // Xóa giỏ hàng sau khi đã tạo hóa đơn
        shoppingCartService.deleteById(customer.getId());

        return savedBill;
    }

    private String generateBillId() {
        return "HD" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
