package com.jwolodzko.car_rental.rental.rental;

import com.jwolodzko.car_rental.payment.PaymentStatus;
import com.jwolodzko.car_rental.rental.AbstractRental;
import com.jwolodzko.car_rental.rental.draft_rental.DraftRental;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Getter
@Table(name = "rental")
public class Rental extends AbstractRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    //for hibernate to map the results from DB
    protected Rental() {
        super();
    }

    //for business use
    public Rental(DraftRental draftRental) {
        if(!Objects.equals(draftRental.getPaymentStatus(), PaymentStatus.COMPLETED) &&
           !Objects.equals(draftRental.getPaymentStatus(), PaymentStatus.REFUNDED)) {
            throw new IllegalArgumentException("Rental payment status cannot be created with status:" + draftRental.getPaymentStatus());
        }

        this.setFromDate(draftRental.getFromDate());
        this.setToDate(draftRental.getToDate());
        this.setClient(draftRental.getClient());
        this.setCar(draftRental.getCar());
        this.setPaymentStatus(draftRental.getPaymentStatus());
    }
}
