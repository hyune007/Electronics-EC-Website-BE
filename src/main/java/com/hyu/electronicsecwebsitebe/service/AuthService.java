package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(String email, String password);
}
