package org.example.smarthotelbookingwebsite.service;

public interface AuthService {
    String generateOTP(String email) ;
    void sendEmail(String email, String otp);
}
