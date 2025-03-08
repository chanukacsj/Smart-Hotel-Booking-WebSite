package org.example.smarthotelbookingwebsite.service.impl;

import org.example.smarthotelbookingwebsite.dto.ReviewDTO;
import org.example.smarthotelbookingwebsite.entity.Hotel;
import org.example.smarthotelbookingwebsite.entity.Review;
import org.example.smarthotelbookingwebsite.entity.User;
import org.example.smarthotelbookingwebsite.repo.HotelRepository;
import org.example.smarthotelbookingwebsite.repo.ReviewRepository;
import org.example.smarthotelbookingwebsite.repo.UserRepository;
import org.example.smarthotelbookingwebsite.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void save(ReviewDTO reviewDTO) {
        reviewRepository.save(modelMapper.map(reviewDTO, Review.class));
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public void update(Long id, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + id));

        // Update fields
        User user = userRepository.findById(String.valueOf(reviewDTO.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + reviewDTO.getUserId()));
        existingReview.setUser(user);
        Hotel hotel = hotelRepository.findById(reviewDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + reviewDTO.getHotelId()));
        existingReview.setHotel(hotel);
        existingReview.setRating(reviewDTO.getRating());
        existingReview.setComment(reviewDTO.getComment());
        existingReview.setReviewDate(reviewDTO.getReviewDate());

        // Save the updated entity
        reviewRepository.save(existingReview);
    }

    @Override
    public List<ReviewDTO> getAll() {
        return modelMapper.map(reviewRepository.findAll(),new TypeToken<List<ReviewDTO>>() {}.getType());
    }
}
