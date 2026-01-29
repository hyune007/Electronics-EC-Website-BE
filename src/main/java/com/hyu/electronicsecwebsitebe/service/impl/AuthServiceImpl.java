package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.dto.request.auth.LoginRequest;
import com.hyu.electronicsecwebsitebe.dto.request.auth.RegisterRequest;
import com.hyu.electronicsecwebsitebe.dto.response.auth.LoginResponse;
import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.model.Role;
import com.hyu.electronicsecwebsitebe.repository.CustomerRepository;
import com.hyu.electronicsecwebsitebe.repository.RoleRepository;
import com.hyu.electronicsecwebsitebe.service.AuthService;
import com.hyu.electronicsecwebsitebe.service.CustomerService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    @Value("${JWT_SIGNER_KEY}")
    private String SECRET_KEY;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public LoginResponse login(String email, String password) {
        Customer customer = customerRepository.findByEmail (email);
        if (customer == null) {
            return null;
        }
        var token = generateJWToken (customer.getId (), customer.getRole ().getId ());
        boolean isPasswordMatch = passwordEncoder.matches (password, customer.getPassword ());
        if (isPasswordMatch) {
            return LoginResponse.builder ()
                    .token (token)
                    .build ();
        }
        return null;
    }

    @Override
    public boolean isAuthenticated(LoginRequest loginRequest) {
        Customer customer = customerRepository.findByEmail (loginRequest.getEmail ());
        if (customer == null) {
            return false;
        }

        return passwordEncoder.matches (loginRequest.getPassword (), customer.getPassword ());
    }

    @Override
    public Customer register(RegisterRequest registerRequest) {
        Customer existingCustomer = customerRepository.findByEmail (registerRequest.getEmail ());
        if (existingCustomer != null) {
            return null;
        }

        Role customerRole = roleRepository.findById ("ROLE_CUSTOMER")
                .orElseThrow (() -> new RuntimeException ("Role ROLE_CUSTOMER không tồn tại trong hệ thống"));

        Customer customer = new Customer ();
        customer.setName (registerRequest.getName ());
        customer.setEmail (registerRequest.getEmail ());
        customer.setPassword (registerRequest.getPassword ());
        customer.setPhone (registerRequest.getPhone ());
        customer.setRole (customerRole);

        return customerService.saveCustomer (customer);
    }

    @Override
    public String generateJWToken(String id, String roleId) {
        JWSHeader jwsHeader = new JWSHeader (JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder ()
                .subject (id)
                .claim ("roleId", roleId)
                .issueTime (new Date ())
                .expirationTime (new Date (Instant.now ().plus (30, ChronoUnit.DAYS).toEpochMilli ()))
                .build ();

        Payload payload = new Payload (jwtClaimsSet.toJSONObject ());

        JWSObject jwsObject = new JWSObject (jwsHeader, payload);

        try {
            jwsObject.sign (new MACSigner (SECRET_KEY.getBytes ()));
            return jwsObject.serialize ();
        } catch (JOSEException e) {
            throw new RuntimeException (e);
        }
    }
}
