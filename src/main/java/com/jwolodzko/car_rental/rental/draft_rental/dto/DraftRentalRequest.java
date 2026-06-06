package com.jwolodzko.car_rental.rental.draft_rental.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record DraftRentalRequest(
        @NotNull @FutureOrPresent LocalDate fromDate,
        @NotNull LocalDate toDate,
        @NotNull @Positive Long clientId,
        @NotNull @Positive Long carId) {
}
