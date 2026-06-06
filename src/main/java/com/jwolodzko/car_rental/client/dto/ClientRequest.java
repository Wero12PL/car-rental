package com.jwolodzko.car_rental.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClientRequest(@NotBlank String name,
                            @NotBlank String surname,
                            @NotBlank String address,
                            @NotNull LocalDate birthDate,
                            @NotBlank @Email String email,
                            String phoneNumber,
                            String company) {
}
