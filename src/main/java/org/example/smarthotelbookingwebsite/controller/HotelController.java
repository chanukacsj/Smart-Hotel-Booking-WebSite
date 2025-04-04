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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final HotelServiceImpl hotelServiceImpl;
    @Autowired
    JwtUtil jwtUtil;

    public HotelController(HotelService hotelService, HotelServiceImpl hotelServiceImpl) {
        this.hotelService = hotelService;
        this.hotelServiceImpl = hotelServiceImpl;
    }
    @PostMapping(value = "/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity<ResponseDTO> saveHotel(@RequestBody @Valid HotelDto hotelDto,@RequestHeader("Authorization") String token){

        System.out.println(hotelDto.getUserId());
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        hotelService.saveHotel(hotelDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Hotel Saved Successfully", null));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity <ResponseDTO> deleteHotel(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        System.out.println("Delete Hotel ID: " + id);
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        hotelService.deleteHotel(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", null));
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity<ResponseDTO> updateHotel(@PathVariable Long id, @RequestBody @Valid HotelDto hotelDto,@RequestHeader("Authorization") String token) {
        System.out.println(hotelDto.getImage());

        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        hotelServiceImpl.updateHotel(id,hotelDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Hotel Updated Successfully", null));
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager','USER')")
    public ResponseEntity<ResponseDTO>getAllHotels(@RequestHeader("Authorization") String token) {
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK,"Success",hotelServiceImpl.getAllHotels()));
    }
}
