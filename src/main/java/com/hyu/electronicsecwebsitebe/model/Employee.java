package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "nhanvien")
public class Employee {
    @Id
    @Column(name = "nv_id")
    private String id;

    @Column(name = "nv_name")
    private String name;

    @Column(name = "nv_password")
    private String password;

    @Column(name = "nv_phone")
    private String phone;

    @Column(name = "nv_mail")
    private String email;

    @Column(name = "nv_address")
    private String address;

    @Column(name = "nv_birth")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "nv_role")
    private Role role;
}
