package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.dto.request.auth.LoginRequest;
import com.hyu.electronicsecwebsitebe.dto.request.auth.RegisterEmployeeRequest;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.UUID;

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

    @Value("${google.client-id}")
    private String googleClientId;

    // CUSTOMER

    @Override
    public LoginResponse login(String email, String password) {
        Customer customer = customerRepository.findByEmail (email);
        if (customer == null) {
            return null;
        }

        if (!passwordEncoder.matches (password, customer.getPassword ())) {
            return null;
        }

        String token = jwtUtil.generateToken (customer.getId (), customer.getRole ().getId ());

        return LoginResponse.builder ()
                .token (token)
//                .userId (customer.getId ())
//                .name (customer.getName ())
//                .email (customer.getEmail ())
//                .roleId (customer.getRole ().getId ())
//                .roleName (customer.getRole ().getName ())
                .build ();
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

    // GOOGLE OAUTH

    @Override
    public LoginResponse loginWithGoogle(String idToken) {
        // Xác minh Google ID Token bằng Google tokeninfo endpoint
        Map<String, Object> googleUser;
        try {
            RestClient restClient = RestClient.create ();
            googleUser = restClient.get ()
                    .uri ("https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken)
                    .retrieve ()
                    .body (new ParameterizedTypeReference<> () {
                    });
        } catch (Exception e) {
            throw new RuntimeException ("Google ID Token không hợp lệ");
        }

        if (googleUser == null || googleUser.containsKey ("error")) {
            throw new RuntimeException ("Google ID Token không hợp lệ");
        }

        // Kiểm tra audience (client_id) có khớp không
        String aud = (String) googleUser.get ("aud");
        if (!googleClientId.equals (aud)) {
            throw new RuntimeException ("Google ID Token không dành cho ứng dụng này");
        }

        String email = (String) googleUser.get ("email");
        String name = (String) googleUser.get ("name");

        if (email == null || email.isEmpty ()) {
            throw new RuntimeException ("Không lấy được email từ Google");
        }

        // Tìm customer theo email, nếu chưa có thì tạo mới
        Customer customer = customerRepository.findByEmail (email);
        if (customer == null) {
            Role customerRole = roleRepository.findById ("ROLE_CUSTOMER")
                    .orElseThrow (() -> new RuntimeException ("Role ROLE_CUSTOMER không tồn tại trong hệ thống"));

            customer = new Customer ();
            customer.setName (name != null ? name : email.split ("@")[0]);
            customer.setEmail (email);
            // Tạo mật khẩu ngẫu nhiên cho tài khoản Google (người dùng không cần biết)
            customer.setPassword (UUID.randomUUID ().toString ());
            customer.setRole (customerRole);

            customer = customerService.saveCustomer (customer);
        }

        // Tạo JWT token
        String token = jwtUtil.generateToken (customer.getId (), customer.getRole ().getId ());

        return LoginResponse.builder ()
                .token (token)
                .build ();
    }

    // EMPLOYEE

    @Override
    public LoginResponse loginEmployee(String email, String password) {
        Employee employee = employeeRepository.findByEmail (email);
        if (employee == null) {
            return null;
        }

        if (!passwordEncoder.matches (password, employee.getPassword ())) {
            return null;
        }

        String token = jwtUtil.generateToken (employee.getId (), employee.getRole ().getId ());

        return LoginResponse.builder ()
                .token (token)
//                .userId (employee.getId ())
//                .name (employee.getName ())
//                .email (employee.getEmail ())
//                .roleId (employee.getRole ().getId ())
//                .roleName (employee.getRole ().getName ())
                .build ();
    }

    @Override
    public boolean isAuthenticatedEmployee(LoginRequest loginRequest) {
        Employee employee = employeeRepository.findByEmail (loginRequest.getEmail ());
        if (employee == null) {
            return false;
        }
        return passwordEncoder.matches (loginRequest.getPassword (), employee.getPassword ());
    }

    @Override
    public Employee registerEmployee(RegisterEmployeeRequest registerEmployeeRequest) {
        Employee existingEmployee = employeeRepository.findByEmail (registerEmployeeRequest.getEmail ());
        if (existingEmployee != null) {
            return null;
        }

        Role employeeRole = roleRepository.findById ("ROLE_EMPLOYEE")
                .orElseThrow (() -> new RuntimeException ("Role ROLE_EMPLOYEE không tồn tại trong hệ thống"));

        Employee employee = new Employee ();
        employee.setName (registerEmployeeRequest.getName ());
        employee.setEmail (registerEmployeeRequest.getEmail ());
        employee.setPassword (registerEmployeeRequest.getPassword ());
        employee.setPhone (registerEmployeeRequest.getPhone ());
        employee.setAddress (registerEmployeeRequest.getAddress ());
        employee.setBirthday (registerEmployeeRequest.getBirthDate ());
        employee.setRole (employeeRole);

        return employeeService.createEmployee (employee);
    }
}
