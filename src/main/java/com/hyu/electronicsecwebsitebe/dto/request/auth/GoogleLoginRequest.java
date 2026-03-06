package com.hyu.electronicsecwebsitebe.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GoogleLoginRequest {
    @NotBlank(message = "Google ID Token không được để trống")
    private String idToken;
}

