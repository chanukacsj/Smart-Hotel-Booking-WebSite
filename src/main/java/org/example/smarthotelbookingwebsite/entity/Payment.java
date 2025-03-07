package org.example.smarthotelbookingwebsite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method; // CREDIT_CARD, PAYPAL, BANK_TRANSFER

    private LocalDateTime paymentDate;

    enum PaymentMethod {
        CREDIT_CARD, PAYPAL, BANK_TRANSFER
    }
}


