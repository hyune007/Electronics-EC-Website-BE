package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.dto.request.CreateBillRequest;
import com.hyu.electronicsecwebsitebe.model.Address;
import com.hyu.electronicsecwebsitebe.model.Bill;
import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.model.Employee;
import com.hyu.electronicsecwebsitebe.repository.AddressRepository;
import com.hyu.electronicsecwebsitebe.repository.CustomerRepository;
import com.hyu.electronicsecwebsitebe.repository.EmployeeRepository;
import com.hyu.electronicsecwebsitebe.service.impl.BillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    BillServiceImpl billService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

//    @GetMapping("/customer/{id}")
//    public List<Bill> billByCustomer (String id) {
//        return billService.findByCustomerId(id);
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createBill(
            @RequestParam String customerId,
            @RequestParam(required = false) String employeeId,
            @RequestParam String addressId,
            @RequestParam String paymentMethod
    ) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        Employee employee = null;
        if (employeeId != null) {
            employee = employeeRepository.findById(employeeId).orElse(null);
        }

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));

        Bill bill = billService.createbillfromcart(customer, employee, address, paymentMethod);

        return ResponseEntity.ok(bill);
    }
}
/*
INSERT INTO giohang (gh_id, sp_quantity, kh_id, sp_id)
VALUES
('GH0001', 1, 'KH001', 'SP001'),
('GH0002', 2, 'KH001', 'SP003'),
('GH0003', 1, 'KH001', 'SP081'),
('GH0004', 1, 'KH002', 'SP014'),
('GH0005', 1, 'KH002', 'SP072');

INSERT INTO diachi (dc_id, kh_id, dc_city, dc_ward, dc_detailaddress, dc_is_default)
VALUES ('DC001', 'KH001', 'Hồ Chí Minh', 'Quận 1', '123 Nguyễn Thị Minh Khai', 1);

Đây là cái dữ liệu mẫu để test chức năng tạo hóa đơn từ giỏ hàng.   giohang trc diachi
 */
