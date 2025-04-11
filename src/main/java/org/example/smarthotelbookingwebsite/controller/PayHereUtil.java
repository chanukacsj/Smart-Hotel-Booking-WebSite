package org.example.smarthotelbookingwebsite.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PayHereUtil {
    public static String generateHash(String merchantId, Long orderId, String amount, String currency, String merchantSecret) {
        try {
            String input = merchantId + orderId + amount + currency + merchantSecret;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b).toUpperCase();
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
