package org.example.smarthotelbookingwebsite.controller;

import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.PaymentDTO;
import org.example.smarthotelbookingwebsite.dto.PaymentResponse;
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
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentServiceImpl paymentServiceimpl;
    @Autowired
    JwtUtil jwtUtil;

    public PaymentController(PaymentService paymentService, PaymentServiceImpl paymentServiceimpl) {
        this.paymentService = paymentService;
        this.paymentServiceimpl = paymentServiceimpl;
    }
//    @PostMapping("/save")
//    @PreAuthorize("hasAnyAuthority('USER')")
//    public ResponseEntity<ResponseDTO> savePayment(@RequestBody @Valid PaymentDTO paymentDTO,@RequestHeader("Authorization") String token) {
//        paymentServiceimpl.save(paymentDTO);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseDTO(VarList.OK, "Payment Successfully", null));
//    }
//    @DeleteMapping(value = "/delete/{id}")
//    public ResponseEntity <ResponseDTO> deletePayment(@PathVariable Long id,@RequestHeader("Authorization") String token) {
//        paymentService.delete(id);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseDTO(VarList.OK, "Success", null));
//    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<ResponseDTO> updatePayment(@PathVariable Long id, @RequestBody @Valid PaymentDTO paymentDTO,@RequestHeader("Authorization") String token) {
//        paymentServiceimpl.update(id,paymentDTO);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseDTO(VarList.OK, "Payment Updated Successfully", null));
//    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> savePayment(@RequestBody PaymentDTO paymentDTO, @RequestHeader("Authorization") String token) {
        try {
            jwtUtil.getUserRoleCodeFromToken(token.substring(7));

            System.out.println("Payment Amount: " + paymentDTO.getAmount());
            System.out.println("Payment Method: " + paymentDTO.getMethod());
            System.out.println("Booking ID: " + paymentDTO.getBookingId());
            System.out.println("Payment Date: " + paymentDTO.getPaymentDate());

            // Prepare Payment object for PayHere API
            PaymentDTO payment = new PaymentDTO();
            payment.setMerchantId("1230019");  // Replace with your Merchant ID
            payment.setAmount(paymentDTO.getAmount());
            payment.setCurrency("LKR"); // Set currency, in this case, Sri Lankan Rupee
            payment.setBookingId(paymentDTO.getBookingId());
            payment.setPaymentDate(paymentDTO.getPaymentDate());
            payment.setMethod(paymentDTO.getMethod());
            payment.setReturnUrl("http://localhost:8080/payment/success");  // URL after successful payment
            payment.setCancelUrl("http://localhost:8080/payment/cancel");  // URL after cancellation
            payment.setNotifyUrl("http://localhost:8080/payment/notify");  // URL for PayHere notification

            // Call PayHere API to create a payment
            PaymentResponse response = paymentServiceimpl.createPayment(payment); // Call PayHere's API (implement this in PaymentService)

            // Return payment URL for redirection to PayHere
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Payment Initiated", response));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Payment initiation failed", null));
        }
    }


    // Example for success response
    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("payment_id") String paymentId) {
        // Handle successful payment
        return "Payment Successful! Payment ID: " + paymentId;
    }

    // Example for cancellation response
    @GetMapping("/cancel")
    public String paymentCancel() {
        // Handle payment cancellation
        return "Payment Cancelled!";
    }

    // Example for payment notification
    @PostMapping("/notify")
    public void paymentNotify(@RequestBody String notificationData) {
        // Handle payment notification
        System.out.println("Payment Notification: " + notificationData);
    }
    @GetMapping("getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllPayments(@RequestHeader("Authorization") String token) {
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", paymentServiceimpl.getAll()));
    }

}
