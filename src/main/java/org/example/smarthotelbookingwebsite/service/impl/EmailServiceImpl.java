package org.example.smarthotelbookingwebsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        String subject = "Your OTP for Registration";
        String body = "Dear User,\n\n" +
                "Your OTP for registration is: " + otp + "\n\n" +
                "Please enter this OTP to complete your registration.\n\n" +
                "Best regards,\nSmart Hotel Booking Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("chanucsj@gmail.com");

        mailSender.send(message);
        System.out.println("OTP email sent successfully to " + toEmail);
    }
}
