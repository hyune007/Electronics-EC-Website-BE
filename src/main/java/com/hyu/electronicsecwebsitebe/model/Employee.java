package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "nhanvien")
public class Employee {
    @Id
    private String id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String address;
    private Date birthday;
}
