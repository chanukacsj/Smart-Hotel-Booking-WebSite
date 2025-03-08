package org.example.smarthotelbookingwebsite.repo;

import org.example.smarthotelbookingwebsite.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
