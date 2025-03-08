package org.example.smarthotelbookingwebsite.controller;


import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.dto.ReviewDTO;
import org.example.smarthotelbookingwebsite.service.ReviewService;
import org.example.smarthotelbookingwebsite.service.impl.ReviewServiceImpl;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewServiceImpl reviewServiceImpl;


    public ReviewController(ReviewService reviewService, ReviewServiceImpl reviewServiceImpl) {
        this.reviewService = reviewService;
        this.reviewServiceImpl = reviewServiceImpl;
    }
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveReview(@RequestBody @Valid ReviewDTO reviewDTO) {
        reviewServiceImpl.save(reviewDTO);
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
        reviewServiceImpl.update(id,reviewDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Review Updated Successfully", null));
    }
    @GetMapping("getAll")
    public ResponseEntity<ResponseDTO> getAllReviews() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", reviewService.getAll()));
    }
}
