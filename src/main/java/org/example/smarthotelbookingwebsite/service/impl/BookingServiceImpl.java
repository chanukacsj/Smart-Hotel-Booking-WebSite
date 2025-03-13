package org.example.smarthotelbookingwebsite.service.impl;

import org.example.smarthotelbookingwebsite.dto.BookingDTO;
import org.example.smarthotelbookingwebsite.entity.Booking;
import org.example.smarthotelbookingwebsite.entity.Room;
import org.example.smarthotelbookingwebsite.entity.User;
import org.example.smarthotelbookingwebsite.repo.BookingRepository;
import org.example.smarthotelbookingwebsite.repo.RoomRepository;
import org.example.smarthotelbookingwebsite.repo.UserRepository;
import org.example.smarthotelbookingwebsite.service.BookingService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;
    @Override
    public void save(BookingDTO bookingDTO) {
        bookingRepository.save(modelMapper.map(bookingDTO, Booking.class));
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public void update(Long id, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found."));

        // Update fields
        User user = userRepository.findById(String.valueOf(bookingDTO.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + bookingDTO.getUserId()));
        existingBooking.setUser(user);

        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + bookingDTO.getRoomId()));
        existingBooking.setRoom(room);
        existingBooking.setCheckInDate(bookingDTO.getCheckInDate());
        existingBooking.setCheckOutDate(bookingDTO.getCheckOutDate());
        existingBooking.setPhoneNumber(bookingDTO.getPhoneNumber());
        existingBooking.setStatus(Booking.BookingStatus.valueOf(bookingDTO.getStatus()));

        bookingRepository.save(existingBooking);
    }

    @Override
    public List<BookingDTO> getAll() {

        return modelMapper.map(bookingRepository.findAll(), new TypeToken<List<BookingDTO>>() {}.getType());
    }
}
