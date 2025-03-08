package org.example.smarthotelbookingwebsite.repo;

import org.example.smarthotelbookingwebsite.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
