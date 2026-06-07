package com.jwolodzko.car_rental.car;

import com.jwolodzko.car_rental.location.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Version
    Long version;

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
    @JoinColumn(nullable = false)
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
