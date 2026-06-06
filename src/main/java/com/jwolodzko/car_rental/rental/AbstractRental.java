package com.jwolodzko.car_rental.rental;

import com.jwolodzko.car_rental.car.Car;
import com.jwolodzko.car_rental.client.Client;
import com.jwolodzko.car_rental.payment.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractRental {

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Car car;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
