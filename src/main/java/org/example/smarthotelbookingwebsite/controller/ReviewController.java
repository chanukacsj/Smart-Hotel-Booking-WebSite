package org.example.smarthotelbookingwebsite.controller;


import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.dto.ReviewDTO;
import org.example.smarthotelbookingwebsite.dto.UserDTO;
import org.example.smarthotelbookingwebsite.entity.Review;
import org.example.smarthotelbookingwebsite.entity.User;
import org.example.smarthotelbookingwebsite.service.ReviewService;
import org.example.smarthotelbookingwebsite.service.UserService;
import org.example.smarthotelbookingwebsite.service.impl.ReviewServiceImpl;
import org.example.smarthotelbookingwebsite.util.JwtUtil;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/review")
@CrossOrigin(origins = "http://localhost:63342")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewServiceImpl reviewServiceImpl;
    private final UserService userService;
    @Autowired
    JwtUtil jwtUtil;

    public ReviewController(ReviewService reviewService, ReviewServiceImpl reviewServiceImpl, UserService userService) {
        this.reviewService = reviewService;
        this.reviewServiceImpl = reviewServiceImpl;
        this.userService = userService;
    }
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> saveReview(@RequestBody @Valid ReviewDTO reviewDTO,@RequestHeader("Authorization") String token) {
        System.out.println("date"+reviewDTO.getReviewDate());
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        reviewService.save(reviewDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Review Saved Successfully", null));
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity <ResponseDTO> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", null));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateReview(@PathVariable Long id, @RequestBody @Valid ReviewDTO reviewDTO) {
        reviewService.update(id,reviewDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Review Updated Successfully", null));
    }
    @GetMapping("getAll")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> getAllReviews(@RequestHeader("Authorization") String token) {

        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", reviewService.getAll()));
    }
}
