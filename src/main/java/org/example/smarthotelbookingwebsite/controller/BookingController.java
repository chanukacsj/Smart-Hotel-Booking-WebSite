package org.example.smarthotelbookingwebsite.controller;

import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.BookingDTO;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.service.BookingService;
import org.example.smarthotelbookingwebsite.service.EmailService;
import org.example.smarthotelbookingwebsite.service.impl.BookingServiceImpl;
import org.example.smarthotelbookingwebsite.service.impl.EmailServiceImpl;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/booking")
public class BookingController {
    private final BookingService bookingService;
    private final BookingServiceImpl bookingServiceImpl;
    private final EmailServiceImpl emailServiceImpl;
    private final EmailService emailService;

    public BookingController(BookingService bookingService, BookingServiceImpl bookingServiceImpl, EmailServiceImpl emailServiceImpl, EmailService emailService) {
        this.bookingService = bookingService;
        this.bookingServiceImpl = bookingServiceImpl;

        this.emailServiceImpl = emailServiceImpl;
        this.emailService = emailService;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveBooking(@RequestBody @Valid BookingDTO bookingDTO) {
        System.out.println("Check-in Date: " + bookingDTO.getCheckInDate());
        System.out.println("Check-out Date: " + bookingDTO.getCheckOutDate());
        System.out.println("User Email: " + bookingDTO.getEmail());

        bookingServiceImpl.save(bookingDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Booking Saved Successfully", null));
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", null));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateBooking(@PathVariable Long id, @RequestBody @Valid BookingDTO bookingDTO) {
        System.out.println(bookingDTO.getEmail());

        bookingServiceImpl.update(id, bookingDTO);

        if ((bookingDTO.getStatus()).equals("CONFIRMED")) {
            String userEmail = bookingDTO.getEmail();
            String bookingDetails = "Check-in Date: " + bookingDTO.getCheckInDate() + "<br>" +
                    "Check-out Date: " + bookingDTO.getCheckOutDate() + "<br>" +
                    "Room Type: " + bookingDTO.getRoomId();

            String paymentLink = "https://yourpaymentgateway.com/pay?bookingId=" + bookingDTO.getId();

            emailService.sendBookingConfirmationEmail(userEmail, bookingDetails, paymentLink);

        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Booking Updated Successfully", null));
    }

    @GetMapping("getAll")
    public ResponseEntity<ResponseDTO> getAllBookings() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", bookingService.getAll()));
    }
}
