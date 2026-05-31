package com.jwolodzko.car_rental.client;

import com.jwolodzko.car_rental.rental.Rental;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String surname;

    @Column(nullable = false)
    String address;

    @Column(nullable = false)
    LocalDate birthDate;

    @Column(nullable = false)
    String email;

    @OneToMany
    List<Rental> rentals;

    String phoneNumber;

    String company;
}
