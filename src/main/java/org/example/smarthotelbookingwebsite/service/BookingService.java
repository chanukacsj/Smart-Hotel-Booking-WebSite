package org.example.smarthotelbookingwebsite.service;

import org.example.smarthotelbookingwebsite.dto.BookingDTO;
import org.example.smarthotelbookingwebsite.dto.ReviewDTO;

import java.util.List;

public interface BookingService {
    void save(BookingDTO bookingDTO);

    void delete(Long id);

    void update(Long id, BookingDTO bookingDTO);

    List<BookingDTO> getAll();
}
