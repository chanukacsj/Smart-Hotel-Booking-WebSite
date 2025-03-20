package org.example.smarthotelbookingwebsite.service;

import org.example.smarthotelbookingwebsite.dto.HotelDto;
import org.example.smarthotelbookingwebsite.dto.RoomDTO;
import org.example.smarthotelbookingwebsite.entity.Room;

import java.util.List;

public interface RoomService {
    void save(RoomDTO roomDTO);

    void delete(Long id);

    void update(Long id, RoomDTO roomDTO);

    List<RoomDTO> getAll();

    List<Room> getAllRoomsByHotelID(Long id);
}
