package org.example.smarthotelbookingwebsite.controller;

import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.entity.Room;
import org.example.smarthotelbookingwebsite.service.RoomService;
import org.example.smarthotelbookingwebsite.service.impl.RoomServiceImpl;
import org.example.smarthotelbookingwebsite.util.JwtUtil;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/ManagerRoom")
public class ManagerRoomController {
    private final RoomService roomService;
    private final RoomServiceImpl roomServiceImpl;
    @Autowired
    JwtUtil jwtUtil;

    public ManagerRoomController(RoomService roomService, RoomServiceImpl roomServiceImpl) {
        this.roomService = roomService;
        this.roomServiceImpl = roomServiceImpl;
    }
    @GetMapping("/getAllRoomByHotelID")
    @PreAuthorize("hasAnyAuthority('Manager')")

    public ResponseEntity<ResponseDTO> getAllRoomByHotelID(@RequestParam Long hotelID,@RequestHeader("Authorization") String token) {
        System.out.println("Hotel ID: " + hotelID);

        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        List<Room> rooms = roomService.getAllRoomsByHotelID(hotelID);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", rooms));
    }

}
