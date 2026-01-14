package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "khachhang")
public class Customer {
    @Id
    private String id;
    private String name;
    private String password;
    private String phone;
    private String email;

}
