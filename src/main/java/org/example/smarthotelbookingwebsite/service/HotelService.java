package org.example.smarthotelbookingwebsite.service;

import jakarta.transaction.Transactional;
import org.example.smarthotelbookingwebsite.dto.HotelDto;
import org.example.smarthotelbookingwebsite.entity.Hotel;

import java.util.List;

public interface HotelService {
    void saveHotel(HotelDto hotel);

    void deleteHotel(Long id);

    void updateHotel(Long id, HotelDto hotelDto);

    List<HotelDto> getAllHotels();

    Long getUserIdByEmail(String email);

    List<Hotel>getHotelsByUserId(Long userId);
}
