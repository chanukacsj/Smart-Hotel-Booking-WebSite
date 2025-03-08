package org.example.smarthotelbookingwebsite.dto;

import jakarta.persistence.Column;
import org.example.smarthotelbookingwebsite.entity.Room;

import java.util.List;

public class HotelDto {
    private Long id;
    private String name;
    private String location;
    private String description;
    private String amenities;
    private int PhoneNumber;
    private List<RoomDTO> rooms;

    public HotelDto(Long id, String name, String location, String description, String amenities, int phoneNumber, List<RoomDTO> rooms) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.amenities = amenities;
        PhoneNumber = phoneNumber;
        this.rooms = rooms;
    }

    public HotelDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "HotelDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", amenities='" + amenities + '\'' +
                ", PhoneNumber=" + PhoneNumber +
                ", rooms=" + rooms +
                '}';
    }
}
