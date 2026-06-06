package com.jwolodzko.car_rental.location;

import com.jwolodzko.car_rental.location.dto.LocationRequest;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void createLocation(LocationRequest locationRequest) {
        Location location = new Location();
        location.setName(locationRequest.name());
        location.setCity(locationRequest.city());
        location.setCountry(locationRequest.country());

        locationRepository.save(location);
    }
}
