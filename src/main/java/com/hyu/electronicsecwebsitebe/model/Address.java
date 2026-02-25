package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "diachi", uniqueConstraints = {@UniqueConstraint(columnNames = {"kh_id", "dc_city", "dc_ward", "dc_detailaddress"})})
public class Address {
    @Id
    @UuidGenerator
    @Column(name = "dc_id", length = 36)
    private String id;

    @Column(name = "dc_city")
    private String city;

    @Column(name = "dc_ward")
    private String ward;

    @Column(name = "dc_detailaddress")
    private String detailAddress;

    @Column(name = "dc_is_default")
    private boolean isDefault = false;

    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;
}
