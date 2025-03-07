package org.example.smarthotelbookingwebsite.controller;

import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.HotelDto;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.service.HotelService;
import org.example.smarthotelbookingwebsite.service.impl.HotelServiceImpl;
import org.example.smarthotelbookingwebsite.util.JwtUtil;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final HotelServiceImpl hotelServiceImpl;

    public HotelController(HotelService hotelService, HotelServiceImpl hotelServiceImpl) {
        this.hotelService = hotelService;
        this.hotelServiceImpl = hotelServiceImpl;
    }
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveUser(@RequestBody @Valid HotelDto hotelDto) {
        System.out.println("phone number"+hotelDto.getPhoneNumber());
        hotelServiceImpl.saveHotel(hotelDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Hotel Saved Successfully", null));
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity <ResponseDTO> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", null));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateHotel(@PathVariable Long id, @RequestBody @Valid HotelDto hotelDto) {
        hotelServiceImpl.updateHotel(id,hotelDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Hotel Updated Successfully", null));
    }
    @GetMapping("getAll")
    public ResponseEntity<ResponseDTO> getAllHotels() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", hotelService.getAllHotels()));
    }
}
