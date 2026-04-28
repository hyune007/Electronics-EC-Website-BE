package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.dto.request.ReturnRequest;
import com.hyu.electronicsecwebsitebe.model.*;
import com.hyu.electronicsecwebsitebe.repository.AddressRepository;
import com.hyu.electronicsecwebsitebe.repository.CustomerRepository;
import com.hyu.electronicsecwebsitebe.repository.EmployeeRepository;
import com.hyu.electronicsecwebsitebe.service.impl.BillServiceImpl;
import com.hyu.electronicsecwebsitebe.service.impl.GhnLocationServiceImpl;
import com.hyu.electronicsecwebsitebe.service.impl.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;

    @Autowired
    private GhnLocationServiceImpl ghnLocationService;

    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok (billService.getAllBills ());
    }

    @GetMapping("/by-employee")
    public ResponseEntity<List<Bill>> getBillsByEmployee(
            @RequestParam String employeeId
    ) {
        return ResponseEntity.ok(billService.getBillsByEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Bill>> billByCustomerId(@PathVariable String customerId) {
        List<Bill> bill = billService.findByCustomerId (customerId);
        return ResponseEntity.ok (bill);
    }

    @GetMapping("/shipping-fee/{customerId}/{addressId}")
    public ResponseEntity<BigDecimal> calculateShippingFee(
            @PathVariable String customerId,
            @PathVariable String addressId
    ) {
        List<ShoppingCart> cartItems = shoppingCartService.findByCustomerId(customerId);
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));
        BigDecimal totalBigDecimal = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int totalAmount = totalBigDecimal.intValue();
        BigDecimal fee = BigDecimal.valueOf(
                ghnLocationService.calculateShippingFee(address, totalAmount)
        );

        return ResponseEntity.ok(fee);
    }
    @PutMapping("update-status/{billId}")
    public ResponseEntity<?> updateBillStatus(
            @PathVariable String billId,
            @RequestParam String status,
            @RequestParam(required = false) String employeeId
    ) {
        try {
            Bill updatedBill = billService.updateBillStatus(billId, status, employeeId);
            return ResponseEntity.ok(updatedBill);
        } catch (Exception e) {
            return ResponseEntity.badRequest ().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBill(
            @RequestParam String customerId,
            @RequestParam(required = false) String employeeId,
            @RequestParam String addressId,
            @RequestParam String paymentMethod
    ) {
        Customer customer = customerRepository.findById (customerId)
                .orElseThrow (() -> new RuntimeException ("Không tìm thấy khách hàng"));

        Employee employee = null;
        if (employeeId != null) {
            employee = employeeRepository.findById (employeeId).orElse (null);
        }

        Address address = addressRepository.findById (addressId)
                .orElseThrow (() -> new RuntimeException ("Không tìm thấy địa chỉ"));

        Bill bill = billService.createbillfromcart (customer, employee, address, paymentMethod);

        return ResponseEntity.status(HttpStatus.CREATED).body(bill);
    }

    @PutMapping("/request-return")
    public ResponseEntity<?> requestReturnBill(@RequestBody ReturnRequest request){
        try {
            Bill bill = billService.requestReturnBill(
                    request.getBillId(),
                    request.getReason(),
                    request.getReturnItems()
            );
            return ResponseEntity.ok (bill);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/approve-return")
    public ResponseEntity<?> approveReturnBill(@RequestParam String billId){
        try {
            Bill bill = billService.approveReturnBill(billId);
            return ResponseEntity.ok (bill);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/reject-return")
    public ResponseEntity<?> rejectReturnBill(@RequestParam String billId){
        try {
            Bill bill = billService.rejectReturnBill(billId);
            return ResponseEntity.ok (bill);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
