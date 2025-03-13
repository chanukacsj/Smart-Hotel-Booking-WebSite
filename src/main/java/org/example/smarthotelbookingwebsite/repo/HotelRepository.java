package org.example.smarthotelbookingwebsite.repo;

import org.example.smarthotelbookingwebsite.entity.Hotel;
import org.example.smarthotelbookingwebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository  extends JpaRepository<Hotel,Long> {
    Long id(Long id);
}
