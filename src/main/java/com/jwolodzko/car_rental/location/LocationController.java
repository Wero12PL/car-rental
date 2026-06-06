package com.jwolodzko.car_rental.location;

import com.jwolodzko.car_rental.location.dto.LocationRequest;
import com.jwolodzko.car_rental.location.dto.LocationResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationRepository locationRepository;

    public LocationController(LocationService locationService, LocationRepository locationRepository) {
        this.locationService = locationService;
        this.locationRepository = locationRepository;
    }

    @PostMapping
    public void createLocation(@Valid @RequestBody LocationRequest locationRequest) {
        locationService.createLocation(locationRequest);
    }

    @GetMapping
    public List<LocationResponse> getLocations() {
        return locationRepository.findAll().stream().map(LocationResponse::new).toList();
    }
}
