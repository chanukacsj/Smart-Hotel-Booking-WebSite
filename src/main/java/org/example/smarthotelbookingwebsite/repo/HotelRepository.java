package org.example.smarthotelbookingwebsite.repo;

import org.example.smarthotelbookingwebsite.entity.Hotel;
import org.example.smarthotelbookingwebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository  extends JpaRepository<Hotel,Long> {
    Long id(Long id);

    @Query("SELECT h FROM Hotel h WHERE h.user.id = :userId")
    List<Hotel> findByUserId(@Param("userId") Long userId);
//    List<Hotel> findByUserId(Long userId);
}
