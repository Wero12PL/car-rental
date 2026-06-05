package com.jwolodzko.car_rental.location;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    @PostMapping
    public void createLocation(@RequestBody Location location) {
        locationRepository.save(location);
    }

    @GetMapping
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }
}
