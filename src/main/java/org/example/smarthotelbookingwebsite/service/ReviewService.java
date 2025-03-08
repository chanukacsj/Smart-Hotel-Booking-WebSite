package org.example.smarthotelbookingwebsite.service;

import org.example.smarthotelbookingwebsite.dto.ReviewDTO;
import org.example.smarthotelbookingwebsite.dto.RoomDTO;

import java.util.List;

public interface ReviewService {
    void save(ReviewDTO reviewDTO);

    void delete(Long id);

    void update(Long id, ReviewDTO reviewDTO);

    List<ReviewDTO> getAll();
}
