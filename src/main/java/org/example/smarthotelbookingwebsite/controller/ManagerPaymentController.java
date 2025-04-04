package org.example.smarthotelbookingwebsite.controller;

import org.example.smarthotelbookingwebsite.dto.PaymentDTO;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.entity.Payment;
import org.example.smarthotelbookingwebsite.service.PaymentService;
import org.example.smarthotelbookingwebsite.service.impl.PaymentServiceImpl;
import org.example.smarthotelbookingwebsite.util.JwtUtil;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/ManagerPayment")
public class ManagerPaymentController {
    private final PaymentService paymentService;
    private final PaymentServiceImpl paymentServiceimpl;
    @Autowired
    JwtUtil jwtUtil;

    public ManagerPaymentController(PaymentService paymentService, PaymentServiceImpl paymentServiceimpl) {
        this.paymentService = paymentService;
        this.paymentServiceimpl = paymentServiceimpl;
    }
    @GetMapping("getAllPaymentsByHotel")
    @PreAuthorize("hasAnyAuthority('Manager')")

    public ResponseEntity<ResponseDTO> getAllPaymentsByHotel(@RequestParam Long hotelID,@RequestHeader("Authorization") String token) {
        System.out.println("Fetching payments for hotel ID: " + hotelID);

        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
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
