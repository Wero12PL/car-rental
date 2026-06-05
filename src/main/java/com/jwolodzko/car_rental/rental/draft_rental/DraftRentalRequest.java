package com.jwolodzko.car_rental.rental.draft_rental;

import com.jwolodzko.car_rental.payment.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DraftRentalRequest {
    private LocalDate fromDate;
    private LocalDate toDate;
    private Long clientId;
    private Long carId;
}
