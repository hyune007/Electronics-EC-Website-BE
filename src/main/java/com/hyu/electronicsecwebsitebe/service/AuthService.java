package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.dto.request.auth.LoginRequest;
import com.hyu.electronicsecwebsitebe.dto.request.auth.RegisterEmployeeRequest;
import com.hyu.electronicsecwebsitebe.dto.request.auth.RegisterRequest;
import com.hyu.electronicsecwebsitebe.dto.response.auth.LoginResponse;
import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.model.Employee;

public interface AuthService {
    // Customer
    LoginResponse login(String email, String password);

    boolean isAuthenticated(LoginRequest loginRequest);

    Customer register(RegisterRequest registerRequest);

    // Google OAuth
    LoginResponse loginWithGoogle(String idToken);

    // Employee
    LoginResponse loginEmployee(String email, String password);

    boolean isAuthenticatedEmployee(LoginRequest loginRequest);

    Employee registerEmployee(RegisterEmployeeRequest registerEmployeeRequestRequest);
}
