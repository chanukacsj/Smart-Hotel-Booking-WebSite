package org.example.smarthotelbookingwebsite.controller;

import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.entity.Hotel;
import org.example.smarthotelbookingwebsite.service.HotelService;
import org.example.smarthotelbookingwebsite.service.impl.HotelServiceImpl;
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
@RequestMapping("api/v1/hotelManager")
public class ManagerHotelController {
    private final HotelService hotelService;
    private final HotelServiceImpl hotelServiceImpl;
    @Autowired
    JwtUtil jwtUtil;


    public ManagerHotelController(HotelService hotelService, HotelServiceImpl hotelServiceImpl) {
        this.hotelService = hotelService;
        this.hotelServiceImpl = hotelServiceImpl;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('Manager')")
    public ResponseEntity<ResponseDTO> getHotelsByEmail(@RequestParam String email, @RequestHeader("Authorization") String token) {
        System.out.println("Email: " + email);

        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        Long userId = hotelService.getUserIdByEmail(email);
        System.out.println("User ID: " + userId);

        if (userId != null) {
            List<Hotel> hotels = hotelService.getHotelsByUserId(userId);

            if (!hotels.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Success", hotels));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "No Hotels Found", null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.Bad_Request, "User Not Found", null));
        }
    }
}
