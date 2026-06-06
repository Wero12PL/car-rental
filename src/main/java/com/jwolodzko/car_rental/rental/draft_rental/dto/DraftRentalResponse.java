package com.jwolodzko.car_rental.rental.draft_rental.dto;

import com.jwolodzko.car_rental.car.dto.CarResponse;
import com.jwolodzko.car_rental.client.dto.ClientResponse;
import com.jwolodzko.car_rental.payment.PaymentStatus;
import com.jwolodzko.car_rental.rental.draft_rental.DraftRental;

import java.time.LocalDate;

public record DraftRentalResponse(Long id, LocalDate fromDate, LocalDate toDate,
                                  ClientResponse client, CarResponse car, PaymentStatus paymentStatus) {
    public DraftRentalResponse(DraftRental draftRental) {
        this(draftRental.getId(), draftRental.getFromDate(), draftRental.getToDate(),
                new ClientResponse(draftRental.getClient()),
                new CarResponse(draftRental.getCar()),
                draftRental.getPaymentStatus());
    }
}
