package org.example.smarthotelbookingwebsite.controller;

import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.BookingDTO;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.service.BookingService;
import org.example.smarthotelbookingwebsite.service.EmailService;
import org.example.smarthotelbookingwebsite.service.impl.BookingServiceImpl;
import org.example.smarthotelbookingwebsite.service.impl.EmailServiceImpl;
import org.example.smarthotelbookingwebsite.util.JwtUtil;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/booking")
public class BookingController {
    private final BookingService bookingService;
    private final BookingServiceImpl bookingServiceImpl;
    private final EmailServiceImpl emailServiceImpl;
    private final EmailService emailService;
    @Autowired
    JwtUtil jwtUtil;

    public BookingController(BookingService bookingService, BookingServiceImpl bookingServiceImpl, EmailServiceImpl emailServiceImpl, EmailService emailService) {
        this.bookingService = bookingService;
        this.bookingServiceImpl = bookingServiceImpl;

        this.emailServiceImpl = emailServiceImpl;
        this.emailService = emailService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> saveBooking(@RequestBody @Valid BookingDTO bookingDTO,@RequestHeader("Authorization") String token) {
        System.out.println("Check-in Date: " + bookingDTO.getCheckInDate());
        System.out.println("Check-out Date: " + bookingDTO.getCheckOutDate());
        System.out.println("User Email: " + bookingDTO.getEmail());
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));

        bookingServiceImpl.save(bookingDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Booking Saved Successfully", null));
    }


    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity<ResponseDTO> deleteBooking(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", null));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity<ResponseDTO> updateBooking(@PathVariable Long id, @RequestBody @Valid BookingDTO bookingDTO,@RequestHeader("Authorization") String token) {
        System.out.println(bookingDTO.getEmail());
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));

        bookingServiceImpl.update(id, bookingDTO);

        if ((bookingDTO.getStatus()).equals("CONFIRMED")) {
            String userEmail = bookingDTO.getEmail();
            String bookingDetails = "Check-in Date: " + bookingDTO.getCheckInDate() + "<br>" +
                    "Check-out Date: " + bookingDTO.getCheckOutDate() + "<br>" +
                    "Room ID: " + bookingDTO.getRoomId();

            String paymentLink = "https://yourpaymentgateway.com/pay?bookingId=" + bookingDTO.getId();

            emailService.sendBookingConfirmationEmail(userEmail, bookingDetails, paymentLink);

        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Booking Updated Successfully", null));
    }

    @GetMapping("getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllBookings(@RequestHeader("Authorization") String token) {
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", bookingService.getAll()));
    }
}
