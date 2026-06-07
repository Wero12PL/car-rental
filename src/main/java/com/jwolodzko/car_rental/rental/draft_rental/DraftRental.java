package com.jwolodzko.car_rental.rental.draft_rental;

import com.jwolodzko.car_rental.car.Car;
import com.jwolodzko.car_rental.client.Client;
import com.jwolodzko.car_rental.payment.PaymentStatus;
import com.jwolodzko.car_rental.rental.AbstractRental;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Table(name = "draft_rental")
public class DraftRental extends AbstractRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    //for hibernate to map the results from DB
    protected DraftRental() {
        super();
    }

    //for business use
    public DraftRental(LocalDate fromDate,
                       LocalDate toDate,
                       Client client,
                       Car car,
                       PaymentStatus paymentStatus) {
        if(Objects.equals(paymentStatus, PaymentStatus.COMPLETED) ||
           Objects.equals(paymentStatus, PaymentStatus.REFUNDED)) {
            throw new IllegalArgumentException("DraftRental payment status cannot be created with status:" + paymentStatus);
        }
        this.setFromDate(fromDate);
        this.setToDate(toDate);
        this.setClient(client);
        this.setCar(car);
        this.setPaymentStatus(paymentStatus);
    }
}
