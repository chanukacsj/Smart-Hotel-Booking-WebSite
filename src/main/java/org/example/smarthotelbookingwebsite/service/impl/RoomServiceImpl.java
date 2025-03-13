package org.example.smarthotelbookingwebsite.service.impl;

import org.example.smarthotelbookingwebsite.dto.HotelDto;
import org.example.smarthotelbookingwebsite.dto.RoomDTO;
import org.example.smarthotelbookingwebsite.entity.Hotel;
import org.example.smarthotelbookingwebsite.entity.Room;
import org.example.smarthotelbookingwebsite.repo.HotelRepository;
import org.example.smarthotelbookingwebsite.repo.RoomRepository;
import org.example.smarthotelbookingwebsite.service.RoomService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public void save(RoomDTO roomDTO) {
       roomRepository.save(modelMapper.map(roomDTO,Room.class));
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public void update(Long id, RoomDTO roomDTO) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + id));

        // Update fields
        existingRoom.setRoomType(roomDTO.getRoomType());
        existingRoom.setPrice(roomDTO.getPrice());
        existingRoom.setAvailable(roomDTO.getAvailable());
        Hotel hotel = hotelRepository.findById(roomDTO.getHotelID())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomDTO.getHotelID()));
        existingRoom.setHotel(hotel);

        // Save the updated entity
        roomRepository.save(existingRoom);
    }

    @Override
    public List<RoomDTO> getAll() {
        // Define the converter first
        modelMapper.addConverter(new Converter<Room, List<String>>() {
            public List<String> convert(MappingContext<Room, List<String>> context) {
                Room room = context.getSource();
                List<String> images = new ArrayList<>();
                if (room.getImage1() != null) images.add(room.getImage1());
                if (room.getImage2() != null) images.add(room.getImage2());
                if (room.getImage3() != null) images.add(room.getImage3());
                return images;
            }
        });

        // Now map the entities to DTOs
        return modelMapper.map(roomRepository.findAll(), new TypeToken<List<RoomDTO>>() {}.getType());
    }

}
