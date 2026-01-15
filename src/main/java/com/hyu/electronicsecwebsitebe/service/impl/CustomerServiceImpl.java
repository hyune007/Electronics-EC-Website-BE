package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.repository.CustomerRepository;
import com.hyu.electronicsecwebsitebe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll ();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save (customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save (customer);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer findById(String id) {
        return customerRepository.findById (id).orElse (null);
    }

    @Override
    public void deleteById(String id) {
        customerRepository.deleteById (id);
    }

}
