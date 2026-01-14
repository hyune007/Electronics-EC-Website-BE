package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "diachi")
public class Address {
    @Id
    @Column(name = "dc_id", length = 8)
    private String id;

    @Column(name = "dc_city", length = 50)
    private String city;

    @Column(name = "dc_ward", length = 50)
    private String ward;

    @Column(name = "dc_detailaddress", length = 50)
    private String detailAddress;

    @Column(name = "dc_is_default", length = 255)
    private boolean isDefault = false;

    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;
}
