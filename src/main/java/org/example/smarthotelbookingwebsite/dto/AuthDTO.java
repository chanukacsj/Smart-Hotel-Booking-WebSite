package org.example.smarthotelbookingwebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
public class AuthDTO {
    private String email;
    private String token;

    public AuthDTO(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public AuthDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthDTO{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}