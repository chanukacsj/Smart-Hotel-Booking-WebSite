package org.example.smarthotelbookingwebsite.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    void sendOtpEmail(String toEmail, String otp);

    @Async
    void sendBookingConfirmationEmail(String toEmail, String bookingDetails, String paymentLink);
}
