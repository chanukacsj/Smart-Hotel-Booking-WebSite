package org.example.smarthotelbookingwebsite.service.impl;

import org.example.smarthotelbookingwebsite.dto.PaymentDTO;
import org.example.smarthotelbookingwebsite.entity.Booking;
import org.example.smarthotelbookingwebsite.entity.Payment;
import org.example.smarthotelbookingwebsite.repo.BookingRepository;
import org.example.smarthotelbookingwebsite.repo.PaymentRepository;
import org.example.smarthotelbookingwebsite.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional

public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;


    @Override
    public void save(PaymentDTO paymentDTO) {
        paymentRepository.save(modelMapper.map(paymentDTO, Payment.class));
    }

    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, PaymentDTO paymentDTO) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        existingPayment.setAmount(paymentDTO.getAmount());

        try {
            existingPayment.setMethod(Payment.PaymentMethod.valueOf(paymentDTO.getMethod()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid payment method: " + paymentDTO.getMethod());
        }

        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + paymentDTO.getBookingId()));

        existingPayment.setBooking(booking);
        existingPayment.setPaymentDate(paymentDTO.getPaymentDate());

        paymentRepository.save(existingPayment);
    }


    @Override
    public List<PaymentDTO> getAll() {
        return modelMapper.map(paymentRepository.findAll(),new TypeToken<List<PaymentDTO>>() {}.getType());
    }
    public List<Payment> getPaymentsByHotelId(Long hotelId) {
        return paymentRepository.findPaymentsByHotelId(hotelId);
    }
}
