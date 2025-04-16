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
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${app.domain}")
    private String appDomain;


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
    public ResponseEntity<ResponseDTO> saveBooking(@RequestBody @Valid BookingDTO bookingDTO, @RequestHeader("Authorization") String token) {
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
    public ResponseEntity<ResponseDTO> deleteBooking(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", null));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'Manager')")
    public ResponseEntity<ResponseDTO> updateBooking(@PathVariable Long id,
                                                     @RequestBody @Valid BookingDTO bookingDTO,
                                                     @RequestHeader("Authorization") String token) {
        System.out.println(bookingDTO.getEmail());

        // Validate JWT token and user role
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));

        // Update the booking
        bookingServiceImpl.update(id, bookingDTO);

        // If the booking is confirmed, generate the payment link
        if ("CONFIRMED".equals(bookingDTO.getStatus())) {
            String userEmail = bookingDTO.getEmail();

            // Booking details to be sent in the email
            String bookingDetails = "Check-in Date: " + bookingDTO.getCheckInDate() + "<br>" +
                    "Check-out Date: " + bookingDTO.getCheckOutDate() + "<br>" +
                    "Room ID: " + bookingDTO.getRoomId() + "<br>" +
                    "Booking ID: " + id;
            System.out.println("Booking ID_"+id);


            // Constructing the payment link with PayHere
            String paymentLink = appDomain+"/Payment.html?bookingId=" + id;

            // Sending the booking confirmation email with the payment link
            emailService.sendBookingConfirmationEmail(userEmail, bookingDetails, paymentLink);
        }

        // Return success response
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Booking Updated Successfully", null));
    }

    @GetMapping("getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getAllBookings(@RequestHeader("Authorization") String token) {
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", bookingService.getAll()));
    }
}
