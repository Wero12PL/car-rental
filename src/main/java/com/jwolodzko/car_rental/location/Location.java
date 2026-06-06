package com.jwolodzko.car_rental.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jwolodzko.car_rental.car.Car;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity()
@Getter
@Setter
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String country;

    @OneToMany(mappedBy = "location")
    Set<Car> cars;
}
