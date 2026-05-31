package com.jwolodzko.car_rental.car;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "car")
public class Car {
    //with a lot of INSERT requests to DB this might be a bottlenect, as we must wait for DB to assign ID
    //UUID.randomUUID() might be better with scale
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String brand;

    @Column(nullable = false)
    String modelName;

    String description;

    @Column(nullable = false)
    CarType carType;

    @Column(nullable = false)
    Integer price;

    Integer seats;

    Integer doors;

    Gearbox gearbox;

    Integer minimumAge;

    Integer luggageCapacity;

    Integer maxDistance;
}
