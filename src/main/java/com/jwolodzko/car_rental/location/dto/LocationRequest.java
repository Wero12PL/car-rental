package com.jwolodzko.car_rental.location.dto;

import jakarta.validation.constraints.NotBlank;

public record LocationRequest(
        @NotBlank String name,
        @NotBlank String city,
        @NotBlank String country) {
}
