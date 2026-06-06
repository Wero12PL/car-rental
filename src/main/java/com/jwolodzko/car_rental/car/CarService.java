package com.jwolodzko.car_rental.car;

import com.jwolodzko.car_rental.car.dto.CarRequest;
import com.jwolodzko.car_rental.exception.ResourceNotFoundException;
import com.jwolodzko.car_rental.location.Location;
import com.jwolodzko.car_rental.location.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final LocationRepository locationRepository;

    public CarService(CarRepository carRepository, LocationRepository locationRepository) {
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
    }

    public void createCar(CarRequest carRequest) {
        Location location = locationRepository.findById(carRequest.locationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + carRequest.locationId()));

        Car car = new Car();
        car.setBrand(carRequest.brand());
        car.setModelName(carRequest.modelName());
        car.setDescription(carRequest.description());
        car.setCarType(carRequest.carType());
        car.setPrice(carRequest.price());
        car.setLocation(location);
        car.setQuantity(carRequest.quantity());
        car.setSeats(carRequest.seats());
        car.setDoors(carRequest.doors());
        car.setGearbox(carRequest.gearbox());
        car.setMinimumAge(carRequest.minimumAge());
        car.setLuggageCapacity(carRequest.luggageCapacity());
        car.setMaxDistance(carRequest.maxDistance());

        carRepository.save(car);
    }
}
