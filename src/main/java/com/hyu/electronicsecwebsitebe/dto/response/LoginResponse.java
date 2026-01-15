package com.hyu.electronicsecwebsitebe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String id;
    private String name;
    private String roleName;
}
