package com.jwolodzko.car_rental.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jwolodzko.car_rental.location.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "car")
public class Car {
    //with a lot of INSERT requests to DB this might be a bottleneck, as we must wait for DB to assign ID
    //UUID.randomUUID() might be better with scale
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String brand;

    @Column(nullable = false)
    String modelName;

    String description;

    @Column(nullable = false)
    CarType carType;

    @Column(nullable = false)
    Integer price;

    @ManyToOne
    Location location;

    @Column(nullable = false)
    Integer quantity;

    Integer seats;

    Integer doors;

    Gearbox gearbox;

    Integer minimumAge;

    Integer luggageCapacity;

    Integer maxDistance;
}
