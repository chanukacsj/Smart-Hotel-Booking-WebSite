package org.example.smarthotelbookingwebsite.repo;

import org.example.smarthotelbookingwebsite.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
