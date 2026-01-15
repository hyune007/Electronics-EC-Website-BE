package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.dto.response.LoginResponse;
import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.repository.CustomerRepository;
import com.hyu.electronicsecwebsitebe.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public LoginResponse login(String email, String password) {
        Customer customer = customerRepository.findByEmailAndPassword (email, password);
        if (customer != null) {
            String roleName = customer.getRole ().getName ();
            return LoginResponse.builder ()
                    .id (customer.getId ())
                    .name (customer.getName ())
                    .roleName (roleName)
                    .build ();
        }
        return null;
    }
}
