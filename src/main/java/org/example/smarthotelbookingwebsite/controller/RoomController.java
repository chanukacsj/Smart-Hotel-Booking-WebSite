package org.example.smarthotelbookingwebsite.controller;

import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.dto.RoomDTO;
import org.example.smarthotelbookingwebsite.service.RoomService;
import org.example.smarthotelbookingwebsite.service.impl.RoomServiceImpl;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/room")
public class RoomController {
    private final RoomService roomService;
    private final RoomServiceImpl roomServiceImpl;

    public RoomController(RoomService roomService, RoomServiceImpl roomServiceImpl) {
        this.roomService = roomService;
        this.roomServiceImpl = roomServiceImpl;
    }
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveRoom(@RequestBody @Valid RoomDTO roomDTO) {
        System.out.println("hotelID"+" "+roomDTO.getHotelID());
        System.out.println(roomDTO.getImage1());
        System.out.println(roomDTO.getImage2());
         roomServiceImpl.save(roomDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Room Saved Successfully", null));
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity <ResponseDTO> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", null));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateRoom(@PathVariable Long id, @RequestBody @Valid RoomDTO roomDTO) {
        System.out.println(roomDTO.getImage1()+"   Image 1");
        roomServiceImpl.update(id,roomDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Room Updated Successfully", null));
    }
    @GetMapping("getAll")
    public ResponseEntity<ResponseDTO> getAllRooms() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", roomService.getAll()));
    }

}
