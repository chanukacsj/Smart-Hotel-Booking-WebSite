package org.example.smarthotelbookingwebsite;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmartHotelBookingWebSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartHotelBookingWebSiteApplication.class, args);

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();

    }

}
