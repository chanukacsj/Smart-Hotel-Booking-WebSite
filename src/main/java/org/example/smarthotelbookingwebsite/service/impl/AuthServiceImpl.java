package org.example.smarthotelbookingwebsite.service.impl;

import org.example.smarthotelbookingwebsite.config.WebSecurityConfig;
import org.example.smarthotelbookingwebsite.entity.User;
import org.example.smarthotelbookingwebsite.repo.UserRepository;
import org.example.smarthotelbookingwebsite.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender; // For sending OTP via email

    private Map<String, String> otpStorage = new HashMap<>(); // Temporary storage (Use Redis for production)

    public String generateOTP(String email) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        String otp = String.valueOf(new Random().nextInt(999999 - 100000) + 100000); // Generate 6-digit OTP
        otpStorage.put(email, otp); // Store OTP temporarily

        sendEmail(email, otp); // Send OTP via email

        return "OTP sent successfully!";
    }

    public void sendEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        mailSender.send(message);
    }
    public boolean verifyOTP(String email, String otp) {
        if (!otpStorage.containsKey(email)) {
            throw new RuntimeException("OTP expired or invalid!");
        }

        if (otpStorage.get(email).equals(otp)) {
            otpStorage.remove(email); // OTP is used, remove it
            return true;
        } else {
            throw new RuntimeException("Incorrect OTP!");
        }
    }
    public String resetPassword(String email, String newPassword) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password reset successfully!";
    }


}

