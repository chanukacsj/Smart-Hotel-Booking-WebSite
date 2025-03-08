package org.example.smarthotelbookingwebsite.dto;

import java.util.List;

public class RoomDTO {
    private Long id;

    private String roomType; // Single, Double, Suite

    private double price;

    private String available;
    private Long hotelId;

    private List<Long> bookingIds;

    public RoomDTO(Long id, String roomType, double price, String available, Long hotelId, List<Long> bookingIds) {
        this.id = id;
        this.roomType = roomType;
        this.price = price;
        this.available = available;
        this.hotelId = hotelId;
        this.bookingIds = bookingIds;
    }

    public RoomDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public List<Long> getBookingIds() {
        return bookingIds;
    }

    public void setBookingIds(List<Long> bookingIds) {
        this.bookingIds = bookingIds;
    }

    @Override
    public String toString() {
        return "RoomDTO{" + "id=" + id + ", roomType='" + roomType + '\'' + ", price=" + price + ", available='" + available + '\'' + ", hotelId=" + hotelId + ", bookingIds=" + bookingIds + '}';
    }
}
