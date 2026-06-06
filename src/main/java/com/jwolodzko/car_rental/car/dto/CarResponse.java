package com.jwolodzko.car_rental.car.dto;

import com.jwolodzko.car_rental.car.Car;
import com.jwolodzko.car_rental.car.CarType;
import com.jwolodzko.car_rental.car.Gearbox;
import com.jwolodzko.car_rental.location.dto.LocationResponse;

public record CarResponse(Long id, String brand, String modelName, String description,
                          CarType carType, Integer price, LocationResponse location,
                          Integer quantity, Integer seats, Integer doors, Gearbox gearbox,
                          Integer minimumAge, Integer luggageCapacity, Integer maxDistance) {
    public CarResponse(Car car) {
        this(car.getId(), car.getBrand(), car.getModelName(), car.getDescription(),
                car.getCarType(), car.getPrice(),
                new LocationResponse(car.getLocation()),
                car.getQuantity(), car.getSeats(), car.getDoors(), car.getGearbox(),
                car.getMinimumAge(), car.getLuggageCapacity(), car.getMaxDistance());
    }
}
