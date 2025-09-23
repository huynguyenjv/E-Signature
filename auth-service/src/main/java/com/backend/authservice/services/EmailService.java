package com.backend.authservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    public void sendPasswordResetEmail(String email, String resetToken) {
        // Implement email sending logic here
        // You can use Spring Boot Mail, SendGrid, etc.
        String resetUrl = "http://localhost:3000/reset-password?token=" + resetToken;

        log.info("Sending password reset email to: {} with URL: {}", email, resetUrl);

        // TODO: Implement actual email sending
        // Example with Spring Mail:
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setTo(email);
        // message.setSubject("Password Reset Request");
        // message.setText("Click here to reset your password: " + resetUrl);
        // mailSender.send(message);
    }
}
