package org.example.smarthotelbookingwebsite.controller;

import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.PaymentDTO;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.service.PaymentService;
import org.example.smarthotelbookingwebsite.service.impl.PaymentServiceImpl;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentServiceImpl paymentServiceimpl;

    public PaymentController(PaymentService paymentService, PaymentServiceImpl paymentServiceimpl) {
        this.paymentService = paymentService;
        this.paymentServiceimpl = paymentServiceimpl;
    }
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> savePayment(@RequestBody @Valid PaymentDTO paymentDTO) {
        paymentServiceimpl.save(paymentDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Payment Saved Successfully", null));
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity <ResponseDTO> deletePayment(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", null));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updatePayment(@PathVariable Long id, @RequestBody @Valid PaymentDTO paymentDTO) {
        paymentServiceimpl.update(id,paymentDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Payment Updated Successfully", null));
    }
    @GetMapping("getAll")
    public ResponseEntity<ResponseDTO> getAllPayments() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", paymentServiceimpl.getAll()));
    }
}
