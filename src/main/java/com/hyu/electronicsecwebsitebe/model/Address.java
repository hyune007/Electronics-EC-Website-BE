package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "diachi")
public class Address {
    @Id
    @Column(name = "dc_id")
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

    @Transient
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder ();
        if (detailAddress != null) sb.append (detailAddress);
        if (ward != null) sb.append (sb.length () > 0 ? ", " : "").append (ward);
        if (city != null) sb.append (sb.length () > 0 ? ", " : "").append (city);
        return sb.toString ();
    }
}
