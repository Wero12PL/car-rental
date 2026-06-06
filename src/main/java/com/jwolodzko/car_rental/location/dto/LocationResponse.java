package com.jwolodzko.car_rental.location.dto;

import com.jwolodzko.car_rental.location.Location;

public record LocationResponse(Long id, String name, String city, String country) {
    public LocationResponse(Location location) {
        this(location.getId(), location.getName(), location.getCity(), location.getCountry());
    }
}
