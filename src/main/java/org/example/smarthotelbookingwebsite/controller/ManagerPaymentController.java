package org.example.smarthotelbookingwebsite.controller;

import org.example.smarthotelbookingwebsite.dto.PaymentDTO;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.entity.Payment;
import org.example.smarthotelbookingwebsite.service.PaymentService;
import org.example.smarthotelbookingwebsite.service.impl.PaymentServiceImpl;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/ManagerPayment")
public class ManagerPaymentController {
    private final PaymentService paymentService;
    private final PaymentServiceImpl paymentServiceimpl;

    public ManagerPaymentController(PaymentService paymentService, PaymentServiceImpl paymentServiceimpl) {
        this.paymentService = paymentService;
        this.paymentServiceimpl = paymentServiceimpl;
    }
    @GetMapping("getAllPaymentsByHotel")
    public ResponseEntity<ResponseDTO> getAllPaymentsByHotel(@RequestParam Long hotelID) {
        System.out.println("Fetching payments for hotel ID: " + hotelID);

        List<Payment> payments = paymentServiceimpl.getPaymentsByHotelId(hotelID);
        System.out.println("Payments found: " + payments.size());

        for (Payment payment : payments) {
            System.out.println("Payment ID: " + payment.getId());
            System.out.println("Booking ID: " + payment.getBooking().getId());

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", payments));
    }
}
