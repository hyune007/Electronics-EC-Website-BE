package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer findById(String id);

    Customer findByEmail(String email);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    boolean existsById(String id);

    void deleteById(String id);

    String generateNextId();
}
