package org.example.smarthotelbookingwebsite.service;

import org.example.smarthotelbookingwebsite.dto.PaymentDTO;
import org.example.smarthotelbookingwebsite.dto.RoomDTO;

import java.util.List;

public interface PaymentService {
    void save(PaymentDTO paymentDTO);

    void delete(Long id);

    void update(Long id, PaymentDTO paymentDTO);

    List<PaymentDTO> getAll();
}
