package com.hyu.electronicsecwebsitebe.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
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

    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;

    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "nv_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "dc_id")
    private Address address;

    @OneToMany(mappedBy = "bill")
    @JsonManagedReference
    private List<DetailBill> detailBills = new ArrayList<> ();

    @Transient
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        return detailBills.stream()
                .map(DetailBill::getTotal)
                .reduce(shippingFee, BigDecimal::add);
    }
}
