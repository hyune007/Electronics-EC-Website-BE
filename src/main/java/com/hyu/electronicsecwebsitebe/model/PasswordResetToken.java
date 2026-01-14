package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "password_reset_tokens")
public class PasswordResetToken {
    @Id
    private Long id;
    private String token;
    private LocalDateTime expiryDate;
}
