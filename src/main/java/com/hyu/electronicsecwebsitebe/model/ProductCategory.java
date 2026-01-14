package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "loaisanpham")
public class productCategory {
    @Id
    @Column(name = "lsp_id")
    private String id;
    @Column(name = "lsp_name")
    private String name;
}
