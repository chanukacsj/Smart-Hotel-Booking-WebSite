package org.example.smarthotelbookingwebsite.service;

public interface OtpService {
    public String generateOtp(String email);

    public boolean verifyOtp(String email, String enteredOtp);

    public void clearOtp(String email);
}
