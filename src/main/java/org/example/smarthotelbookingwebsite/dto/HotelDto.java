package org.example.smarthotelbookingwebsite.dto;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.List;

public class HotelDto {
    private Long id;
    private String name;
    private String location;
    private String description;
    private String amenities;
    private String PhoneNumber;
    private List<RoomDTO> rooms;
    private Blob image;

    public HotelDto(Long id, String name, String location, String description, String amenities, String phoneNumber, List<RoomDTO> rooms, Blob image) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.amenities = amenities;
        PhoneNumber = phoneNumber;
        this.rooms = rooms;
        this.image = image;
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "HotelDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", amenities='" + amenities + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", rooms=" + rooms +
                ", image=" + image +
                '}';
    }
}
