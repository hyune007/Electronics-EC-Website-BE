package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.model.PasswordResetToken;
import com.hyu.electronicsecwebsitebe.repository.CustomerRepository;
import com.hyu.electronicsecwebsitebe.repository.PasswordResetTokenRepository;
import com.hyu.electronicsecwebsitebe.service.PasswordResetService;
import com.hyu.electronicsecwebsitebe.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createAndSendPasswordResetToken(String email) {
        Customer customer = customerRepository.findByEmail (email);
        if (customer == null) {
            throw new RuntimeException ("Email không tồn tại trong hệ thống");
        }

        passwordResetTokenRepository.deleteByCustomer (customer);

        String token = UUID.randomUUID ().toString ();
        PasswordResetToken passwordResetToken = new PasswordResetToken ();
        passwordResetToken.setToken (token);
        passwordResetToken.setCustomer (customer);
        passwordResetToken.setExpiryDate (java.time.LocalDateTime.now ().plusMinutes (5));
        passwordResetTokenRepository.save (passwordResetToken);
        String resetLink = "http://localhost:5173/change-password?token=" + token;

        String emailBody = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;">
                    <h2 style="color: #333; text-align: center;">🔐 Đặt lại mật khẩu</h2>
                    <p>Xin chào <strong>%s</strong>,</p>
                    <p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại <strong>UBrainTech</strong>.</p>
                    <p>Vui lòng click vào nút bên dưới để đặt lại mật khẩu:</p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" style="background-color: #4CAF50; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">
                            Đặt lại mật khẩu
                        </a>
                    </div>
                    <p style="color: #666; font-size: 14px;">⏰ Link này sẽ hết hạn sau <strong>5 phút</strong>.</p>
                    <p style="color: #666; font-size: 14px;">Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>
                    <hr style="margin: 20px 0; border: none; border-top: 1px solid #ddd;">
                    <p style="color: #999; font-size: 12px; text-align: center;">© 2025 Poly_UBs - Tech Store</p>
                </div>
                """.formatted (customer.getName (), resetLink);
        MailSender.send (customer.getEmail (), "Yêu cầu đặt lại mật khẩu - UbrainTech", emailBody);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken (token);
        if (passwordResetToken.isEmpty () || passwordResetToken.get ().isExpired ()) {
            throw new RuntimeException ("Token không hợp lệ hoặc đã hết hạn");
        }

        Customer customer = passwordResetToken.get ().getCustomer ();
        customer.setPassword (passwordEncoder.encode (newPassword));
        customerRepository.save (customer);
        passwordResetTokenRepository.delete (passwordResetToken.get ());
    }

    @Override
    public boolean validatePasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken (token)
                .map (resetToken -> !resetToken.isExpired ())
                .orElse (false);
    }
}
