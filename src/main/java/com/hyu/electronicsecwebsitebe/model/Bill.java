package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "hoadon")
public class Bill {
    @Id
    @JoinColumn(name = "hd_id")
    private String id;

    @JoinColumn(name = "hd_date")
    private Date date;

    @JoinColumn(name = "hd_status")
    private String status;

    @JoinColumn(name = "payment_method")
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "nv_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "dc_id")
    private Address address;
}
