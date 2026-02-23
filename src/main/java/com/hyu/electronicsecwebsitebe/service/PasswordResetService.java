package com.hyu.electronicsecwebsitebe.service;

public interface PasswordResetService {
    void createAndSendPasswordResetToken(String email);

    void resetPassword(String token, String newPassword);

    boolean validatePasswordResetToken(String token);
}
