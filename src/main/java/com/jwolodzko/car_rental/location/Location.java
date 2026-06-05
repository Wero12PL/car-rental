package com.jwolodzko.car_rental.location;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    String city;

    @Column(nullable = false)
    String country;
}
