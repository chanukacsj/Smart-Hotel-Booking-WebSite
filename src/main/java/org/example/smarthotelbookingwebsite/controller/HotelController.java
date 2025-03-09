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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final HotelServiceImpl hotelServiceImpl;

    public HotelController(HotelService hotelService, HotelServiceImpl hotelServiceImpl) {
        this.hotelService = hotelService;
        this.hotelServiceImpl = hotelServiceImpl;
    }
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> saveUser(@RequestParam("image") MultipartFile file, @RequestBody @Valid HotelDto hotelDto) throws SQLException, IOException {

            // Convert MultipartFile to byte[] for storage
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

            // Set the image data in the DTO
            hotelDto.setImage(blob);

            // Save hotel details
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
