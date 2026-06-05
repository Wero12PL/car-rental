package com.jwolodzko.car_rental.rental.rental;

import com.jwolodzko.car_rental.car.Car;
import com.jwolodzko.car_rental.client.Client;
import com.jwolodzko.car_rental.payment.PaymentStatus;
import com.jwolodzko.car_rental.rental.AbstractRental;
import jakarta.persistence.*;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Objects;

@Entity
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
    public Rental(@NonNull LocalDate fromDate,
                  @NonNull LocalDate toDate,
                  @NonNull Client client,
                  @NonNull Car car,
                  @NonNull PaymentStatus paymentStatus) {
        if(!Objects.equals(paymentStatus, PaymentStatus.COMPLETED) &&
           !Objects.equals(paymentStatus, PaymentStatus.REFUNDED)) {
            throw new IllegalArgumentException("Rental payment status cannot be created with status:" + paymentStatus);
        }

        this.setFromDate(fromDate);
        this.setToDate(toDate);
        this.setClient(client);
        this.setCar(car);
        this.setPaymentStatus(paymentStatus);
    }
}
