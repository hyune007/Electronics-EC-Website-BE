package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.dto.request.LoginRequest;
import com.hyu.electronicsecwebsitebe.dto.response.LoginResponse;
import com.hyu.electronicsecwebsitebe.service.impl.AuthServiceImpl;
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
    AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login (loginRequest.getEmail (), loginRequest.getPassword ());
        if (loginResponse != null) {
            return ResponseEntity.ok (loginResponse);
        } else {
            return ResponseEntity.status (HttpStatus.UNAUTHORIZED).body ("Email hoặc mật khẩu không đúng");
        }
    }
}
