package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hang")
public class Brand {
    @Id
    @Column(name = "hang_id")
    private String id;

    @Column(name = "hang_name")
    private String name;
}
