package org.example.smarthotelbookingwebsite.service;

import org.example.smarthotelbookingwebsite.dto.HotelDto;
import org.example.smarthotelbookingwebsite.entity.Hotel;

import java.util.List;

public interface HotelService {
    void saveHotel(HotelDto hotel);

    void deleteHotel(Long id);

    void updateHotel(Long id, HotelDto hotelDto);

    List<HotelDto> getAllHotels();


}
