package org.example.smarthotelbookingwebsite.service;

import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.HotelDto;
import org.example.smarthotelbookingwebsite.dto.UserDTO;

import java.util.List;

public interface HotelService {
    void saveHotel(HotelDto hotelDto);
    void deleteHotel(Long id);

    void updateHotel(Long id,HotelDto hotelDto);

     List<HotelDto> getAllHotels();
}
