package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers ();
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable String id) {
        return customerService.findById (id);
    }

    @GetMapping("/mail/{email}")
    public Customer findByEmail(@PathVariable String email) {
        return customerService.findByEmail (email);
    }

    @PostMapping("/save")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer (customer);
    }

    @PutMapping("/update/{id}")
    public Customer updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        customer.setId (id);
        return customerService.updateCustomer (customer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable String id) {
        customerService.deleteById (id);
    }

}
