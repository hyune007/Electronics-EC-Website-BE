package com.hyu.electronicsecwebsitebe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "hoadon")
public class Bill {
    @Id
    @Column(name = "hd_id")
    private String id;

    @Column(name = "hd_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "hd_status")
    private String status;

    @Column(name = "payment_method")
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

    @JsonIgnore
    @OneToMany(mappedBy = "bill")
    private List<DetailBill> detailBills = new ArrayList<> ();
}
