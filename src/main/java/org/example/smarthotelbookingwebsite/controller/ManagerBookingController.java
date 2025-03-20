package org.example.smarthotelbookingwebsite.controller;

import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.entity.Booking;
import org.example.smarthotelbookingwebsite.service.BookingService;
import org.example.smarthotelbookingwebsite.service.impl.BookingServiceImpl;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/ManagerBooking")
public class ManagerBookingController {
    private final BookingService bookingService;
    private final BookingServiceImpl bookingServiceImpl;

    public ManagerBookingController(BookingService bookingService, BookingServiceImpl bookingServiceImpl) {
        this.bookingService = bookingService;
        this.bookingServiceImpl = bookingServiceImpl;
    }
    @GetMapping("getAllHotelBookings")
    public ResponseEntity<ResponseDTO> getBookingsByHotel(@RequestParam Long hotelID) {

        System.out.println("Fetching bookings for hotel ID: " + hotelID);

        List<Booking> bookings = bookingService.getBookingsByHotel(hotelID);
        System.out.println("Bookings found: " + bookings.size());

        for (Booking booking : bookings) {
            System.out.println("Booking ID: " + booking.getId());
            System.out.println("Room ID: " + booking.getRoom().getId());
            System.out.println("User ID: " + booking.getUser().getId());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", bookings));}
}
