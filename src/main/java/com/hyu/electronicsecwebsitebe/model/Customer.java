package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "khachhang")
public class Customer {
    @Id
    @Column(name = "kh_id")
    private String id;

    @Column(name = "kh_name")
    private String name;

    @Column(name = "kh_password")
    private String password;

    @Column(name = "kh_phone")
    private String phone;

    @Column(name = "kh_mail")
    private String email;


}
