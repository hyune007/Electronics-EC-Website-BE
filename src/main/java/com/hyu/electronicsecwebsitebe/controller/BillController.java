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

        @GetMapping("/customer/{id}")
        public List<Bill> billByCustomer (String id) {
            return billService.findByCustomerId(id);
        }

    @PostMapping("/create")
    public ResponseEntity<?> createBill(@RequestBody CreateBillRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        Employee employee = null;
        if (request.getEmployeeId() != null) {
            employee = employeeRepository.findById(request.getEmployeeId()).orElse(null);
        }

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ giao hàng"));

        Bill bill = billService.createbillfromcart(
                customer,
                employee,
                address,
                request.getPaymentMethod()
        );

        return ResponseEntity.ok(bill);
    }
}
