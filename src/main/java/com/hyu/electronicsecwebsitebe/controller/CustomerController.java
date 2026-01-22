package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> listCustomers = customerService.getAllCustomers ();
        return ResponseEntity.ok (listCustomers);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        if (!customerService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        Customer foundCustomer = customerService.findById (id);
        return ResponseEntity.ok (foundCustomer);
    }

    @GetMapping("/mail/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Customer foundCustomer = customerService.findByEmail (email);
        if (foundCustomer == null) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (foundCustomer);
    }

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        if (customerService.existsById (customer.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
        Customer savedCustomer = customerService.saveCustomer (customer);
        return ResponseEntity.status (201).body (savedCustomer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        if (!customerService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        customer.setId (id);
        Customer updatedCustomer = customerService.updateCustomer (customer);
        return ResponseEntity.ok (updatedCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        if (!customerService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        customerService.deleteById (id);
        return ResponseEntity.noContent ().build ();
    }
}
