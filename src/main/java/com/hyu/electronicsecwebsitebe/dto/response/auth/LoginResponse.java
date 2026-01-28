package com.hyu.electronicsecwebsitebe.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    boolean isAuthenticated;
    String email;
    String roleId;
}
