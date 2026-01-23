package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.repository.CustomerRepository;
import com.hyu.electronicsecwebsitebe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String ID_PREFIX = "KH";
    private static final int ID_NUMBER_LENGTH = 3;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll ();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        if (customer.getId () == null || customer.getId ().isEmpty ()) {
            customer.setId (generateNextId ());
        }
        if (customer.getPassword () != null && !customer.getPassword ().isEmpty ()) {
            String hashedPassword = passwordEncoder.encode (customer.getPassword ());
            customer.setPassword (hashedPassword);
        }
        return customerRepository.save (customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        if (customer.getPassword () != null && !customer.getPassword ().isEmpty ()) {
            if (!customer.getPassword ().startsWith ("$2a$") &&
                    !customer.getPassword ().startsWith ("$2b$") &&
                    !customer.getPassword ().startsWith ("$2y$")) {
                String hashedPassword = passwordEncoder.encode (customer.getPassword ());
                customer.setPassword (hashedPassword);
            }
        }
        return customerRepository.save (customer);
    }

    @Override
    public boolean existsById(String id) {
        return customerRepository.existsById (id);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail (email);
    }

    @Override
    public Customer findById(String id) {
        return customerRepository.findById (id).orElse (null);
    }

    @Override
    public void deleteById(String id) {
        customerRepository.deleteById (id);
    }

    @Override
    public String generateNextId() {
        String maxId = customerRepository.findMaxId ();
        int nextNumber = 1;
        if (maxId != null && maxId.startsWith (ID_PREFIX)) {
            try {
                String numberPart = maxId.substring (ID_PREFIX.length ());
                nextNumber = Integer.parseInt (numberPart) + 1;
            } catch (NumberFormatException ignored) {
            }
        }
        return ID_PREFIX + String.format ("%0" + ID_NUMBER_LENGTH + "d", nextNumber);
    }
}
