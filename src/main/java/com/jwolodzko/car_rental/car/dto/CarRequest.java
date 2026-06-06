package com.jwolodzko.car_rental.car.dto;

import com.jwolodzko.car_rental.car.CarType;
import com.jwolodzko.car_rental.car.Gearbox;

public record CarRequest(String brand, String modelName, String description,
                         CarType carType, Integer price, Long locationId,
                         Integer quantity, Integer seats, Integer doors, Gearbox gearbox,
                         Integer minimumAge, Integer luggageCapacity, Integer maxDistance) {
}
