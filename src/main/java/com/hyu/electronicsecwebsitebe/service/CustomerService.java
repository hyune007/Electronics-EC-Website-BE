package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer findById(String id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void deleteById(String id);
}
