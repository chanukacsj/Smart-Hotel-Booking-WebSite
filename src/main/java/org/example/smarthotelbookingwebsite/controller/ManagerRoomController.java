package org.example.smarthotelbookingwebsite.controller;

import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.entity.Room;
import org.example.smarthotelbookingwebsite.service.RoomService;
import org.example.smarthotelbookingwebsite.service.impl.RoomServiceImpl;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/ManagerRoom")
public class ManagerRoomController {
    private final RoomService roomService;
    private final RoomServiceImpl roomServiceImpl;

    public ManagerRoomController(RoomService roomService, RoomServiceImpl roomServiceImpl) {
        this.roomService = roomService;
        this.roomServiceImpl = roomServiceImpl;
    }
    @GetMapping("/getAllRoomByHotelID")
    public ResponseEntity<ResponseDTO> getAllRoomByHotelID(@RequestParam Long hotelID) {
        System.out.println("Hotel ID: " + hotelID);
        List<Room> rooms = roomService.getAllRoomsByHotelID(hotelID);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", rooms));
    }

}
