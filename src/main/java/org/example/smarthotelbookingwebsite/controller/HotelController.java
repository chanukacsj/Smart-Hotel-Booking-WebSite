package org.example.smarthotelbookingwebsite.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.HotelDto;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.entity.Hotel;
import org.example.smarthotelbookingwebsite.service.HotelService;
import org.example.smarthotelbookingwebsite.service.impl.HotelServiceImpl;
import org.example.smarthotelbookingwebsite.util.JwtUtil;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final HotelServiceImpl hotelServiceImpl;

    public HotelController(HotelService hotelService, HotelServiceImpl hotelServiceImpl) {
        this.hotelService = hotelService;
        this.hotelServiceImpl = hotelServiceImpl;
    }
    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> saveHotel(@RequestBody @Valid HotelDto hotelDto){
        System.out.println(hotelDto.getImage());
        hotelService.saveHotel(hotelDto);
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
        System.out.println(hotelDto.getImage());
        hotelServiceImpl.updateHotel(id,hotelDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Hotel Updated Successfully", null));
    }
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO>getAllHotels() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK,"Succses",hotelServiceImpl.getAllHotels()));
    }
}
