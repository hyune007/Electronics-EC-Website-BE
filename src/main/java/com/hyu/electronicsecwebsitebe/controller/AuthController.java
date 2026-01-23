package com.hyu.electronicsecwebsitebe.controller; //huynt

import com.hyu.electronicsecwebsitebe.dto.request.auth.LoginRequest;
import com.hyu.electronicsecwebsitebe.dto.request.auth.RegisterRequest;
import com.hyu.electronicsecwebsitebe.dto.response.auth.LoginResponse;
import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login (loginRequest.getEmail (), loginRequest.getPassword ());
        if (loginResponse != null) {
            return ResponseEntity.ok (loginResponse);
        } else {
            return ResponseEntity.status (HttpStatus.UNAUTHORIZED).body ("Email hoặc mật khẩu không đúng");
        }
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
}
