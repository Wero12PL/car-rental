package com.jwolodzko.car_rental.car.dto;

import com.jwolodzko.car_rental.car.CarType;
import com.jwolodzko.car_rental.car.Gearbox;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CarRequest(@NotBlank String brand,
                         @NotBlank String modelName,
                         @NotBlank String description,
                         @NotNull CarType carType,
                         @NotNull @Positive Integer price,
                         @NotNull Long locationId,
                         @NotNull @PositiveOrZero Integer quantity,
                         Integer seats,
                         Integer doors,
                         Gearbox gearbox,
                         Integer minimumAge,
                         Integer luggageCapacity,
                         Integer maxDistance) {
}
