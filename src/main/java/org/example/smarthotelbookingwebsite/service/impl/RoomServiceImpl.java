package org.example.smarthotelbookingwebsite.service.impl;

import org.example.smarthotelbookingwebsite.dto.RoomDTO;
import org.example.smarthotelbookingwebsite.entity.Hotel;
import org.example.smarthotelbookingwebsite.entity.Room;
import org.example.smarthotelbookingwebsite.repo.HotelRepository;
import org.example.smarthotelbookingwebsite.repo.RoomRepository;
import org.example.smarthotelbookingwebsite.service.RoomService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Hotel hotel = hotelRepository.findById(roomDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomDTO.getHotelId()));
        existingRoom.setHotel(hotel);




        // Save the updated entity
        roomRepository.save(existingRoom);
    }

    @Override
    public List<Room> getAll() {
        return modelMapper.map(roomRepository.findAll(),new TypeToken<List<Room>>() {}.getType());
    }
}
