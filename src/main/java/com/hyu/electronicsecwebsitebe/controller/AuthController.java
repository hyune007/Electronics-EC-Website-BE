package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.dto.request.auth.ForgotPasswordRequest;
import com.hyu.electronicsecwebsitebe.dto.request.auth.LoginRequest;
import com.hyu.electronicsecwebsitebe.dto.request.auth.RegisterEmployeeRequest;
import com.hyu.electronicsecwebsitebe.dto.request.auth.RegisterRequest;
import com.hyu.electronicsecwebsitebe.dto.request.auth.ResetPasswordRequest;
import com.hyu.electronicsecwebsitebe.dto.response.auth.LoginResponse;
import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.model.Employee;
import com.hyu.electronicsecwebsitebe.service.AuthService;
import com.hyu.electronicsecwebsitebe.service.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordResetService passwordResetService;

    // CUSTOMER

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        if (!authService.isAuthenticated (loginRequest)) {
            return ResponseEntity.status (HttpStatus.UNAUTHORIZED)
                    .body ("Email hoặc mật khẩu không đúng");
        }
        LoginResponse loginResponse = authService.login (loginRequest.getEmail (), loginRequest.getPassword ());
        return ResponseEntity.ok (loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        Customer customer = authService.register (registerRequest);
        if (customer != null) {
            return ResponseEntity.status (HttpStatus.CREATED).body ("Đăng ký thành công");
        } else {
            return ResponseEntity.status (HttpStatus.BAD_REQUEST).body ("Email đã tồn tại trong hệ thống");
        }
    }

    // EMPLOYEE

    @PostMapping("/employee/login")
    public ResponseEntity<?> loginEmployee(@Valid @RequestBody LoginRequest loginRequest) {
        if (!authService.isAuthenticatedEmployee (loginRequest)) {
            return ResponseEntity.status (HttpStatus.UNAUTHORIZED)
                    .body ("Email hoặc mật khẩu không đúng");
        }
        LoginResponse loginResponse = authService.loginEmployee (loginRequest.getEmail (), loginRequest.getPassword ());
        return ResponseEntity.ok (loginResponse);
    }

    @PostMapping("/employee/register")
    public ResponseEntity<?> registerEmployee(
            @Valid @RequestBody RegisterEmployeeRequest registerEmployeeRequestRequest) {
        Employee employee = authService.registerEmployee (registerEmployeeRequestRequest);
        if (employee != null) {
            return ResponseEntity.status (HttpStatus.CREATED).body ("Đăng ký nhân viên thành công");
        } else {
            return ResponseEntity.status (HttpStatus.BAD_REQUEST).body ("Email đã tồn tại trong hệ thống");
        }
    }

    // PASSWORD RESET

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            passwordResetService.createAndSendPasswordResetToken (request.getEmail ());
            return ResponseEntity.ok ("Link đặt lại mật khẩu đã được gửi đến email của bạn");
        } catch (RuntimeException e) {
            return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (e.getMessage ());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            passwordResetService.resetPassword (request.getToken (), request.getNewPassword ());
            return ResponseEntity.ok ("Đặt lại mật khẩu thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (e.getMessage ());
        }
    }

    @GetMapping("/validate-reset-token")
    public ResponseEntity<?> validateResetToken(@RequestParam String token) {
        boolean isValid = passwordResetService.validatePasswordResetToken (token);
        if (isValid) {
            return ResponseEntity.ok ("Token hợp lệ");
        } else {
            return ResponseEntity.status (HttpStatus.BAD_REQUEST).body ("Token không hợp lệ hoặc đã hết hạn");
        }
    }
}
