package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.dto.request.auth.LoginRequest;
import com.hyu.electronicsecwebsitebe.dto.request.auth.RegisterRequest;
import com.hyu.electronicsecwebsitebe.dto.response.auth.LoginResponse;
import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.model.Employee;
import com.hyu.electronicsecwebsitebe.model.Role;
import com.hyu.electronicsecwebsitebe.repository.CustomerRepository;
import com.hyu.electronicsecwebsitebe.repository.EmployeeRepository;
import com.hyu.electronicsecwebsitebe.repository.RoleRepository;
import com.hyu.electronicsecwebsitebe.service.AuthService;
import com.hyu.electronicsecwebsitebe.service.CustomerService;
import com.hyu.electronicsecwebsitebe.service.EmployeeService;
import com.hyu.electronicsecwebsitebe.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CUSTOMER

    @Override
    public LoginResponse login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            return null;
        }

        if (!passwordEncoder.matches(password, customer.getPassword())) {
            return null;
        }

        String token = jwtUtil.generateToken(
                customer.getId(),
                customer.getRole().getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone());

        return LoginResponse.builder()
                .token(token)
                .userId(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .roleId(customer.getRole().getId())
                .roleName(customer.getRole().getName())
                .build();
    }

    @Override
    public boolean isAuthenticated(LoginRequest loginRequest) {
        Customer customer = customerRepository.findByEmail(loginRequest.getEmail());
        if (customer == null) {
            return false;
        }
        return passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword());
    }

    @Override
    public Customer register(RegisterRequest registerRequest) {
        Customer existingCustomer = customerRepository.findByEmail(registerRequest.getEmail());
        if (existingCustomer != null) {
            return null;
        }

        Role customerRole = roleRepository.findById("ROLE_CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role ROLE_CUSTOMER không tồn tại trong hệ thống"));

        Customer customer = new Customer();
        customer.setName(registerRequest.getName());
        customer.setEmail(registerRequest.getEmail());
        customer.setPassword(registerRequest.getPassword());
        customer.setPhone(registerRequest.getPhone());
        customer.setRole(customerRole);

        return customerService.saveCustomer(customer);
    }

    // EMPLOYEE

    @Override
    public LoginResponse loginEmployee(String email, String password) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null) {
            return null;
        }

        if (!passwordEncoder.matches(password, employee.getPassword())) {
            return null;
        }

        String token = jwtUtil.generateToken(
                employee.getId(),
                employee.getRole().getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhone());

        return LoginResponse.builder()
                .token(token)
                .userId(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .roleId(employee.getRole().getId())
                .roleName(employee.getRole().getName())
                .build();
    }

    @Override
    public boolean isAuthenticatedEmployee(LoginRequest loginRequest) {
        Employee employee = employeeRepository.findByEmail(loginRequest.getEmail());
        if (employee == null) {
            return false;
        }
        return passwordEncoder.matches(loginRequest.getPassword(), employee.getPassword());
    }

    @Override
    public Employee registerEmployee(RegisterRequest registerRequest, String roleId) {
        Employee existingEmployee = employeeRepository.findByEmail(registerRequest.getEmail());
        if (existingEmployee != null) {
            return null;
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role " + roleId + " không tồn tại trong hệ thống"));

        Employee employee = new Employee();
        employee.setName(registerRequest.getName());
        employee.setEmail(registerRequest.getEmail());
        employee.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        employee.setPhone(registerRequest.getPhone());
        employee.setRole(role);

        return employeeService.createEmployee(employee);
    }
}
