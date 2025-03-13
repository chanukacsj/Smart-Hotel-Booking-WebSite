package org.example.smarthotelbookingwebsite.service.impl;

import org.example.smarthotelbookingwebsite.dto.HotelDto;
import org.example.smarthotelbookingwebsite.entity.Hotel;
import org.example.smarthotelbookingwebsite.repo.HotelRepository;
import org.example.smarthotelbookingwebsite.service.HotelService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override

    public void saveHotel(HotelDto hotel) {
        hotelRepository.save(modelMapper.map(hotel,Hotel.class));
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public void updateHotel(Long id, HotelDto hotelDto) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + id));

        // Update fields
        existingHotel.setName(hotelDto.getName());
        existingHotel.setLocation(hotelDto.getLocation());
        existingHotel.setDescription(hotelDto.getDescription());
        existingHotel.setAmenities(hotelDto.getAmenities());
        existingHotel.setPhoneNumber(hotelDto.getPhoneNumber());
        existingHotel.setImage(hotelDto.getImage());

        // Save the updated entity
        hotelRepository.save(existingHotel);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        return modelMapper.map(hotelRepository.findAll(), new TypeToken<List<HotelDto>>() {}.getType());

    }

}
