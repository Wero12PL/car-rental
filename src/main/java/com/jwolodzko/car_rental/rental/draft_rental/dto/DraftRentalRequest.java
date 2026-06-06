package com.jwolodzko.car_rental.rental.draft_rental.dto;

import java.time.LocalDate;

public record DraftRentalRequest(LocalDate fromDate, LocalDate toDate, Long clientId, Long carId) {
}
