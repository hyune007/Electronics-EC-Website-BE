package com.hyu.electronicsecwebsitebe.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterEmployeeRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Date birthDate;
}
