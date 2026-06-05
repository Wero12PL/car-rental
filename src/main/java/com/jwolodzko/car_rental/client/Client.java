package com.jwolodzko.car_rental.client;

import com.jwolodzko.car_rental.rental.draft_rental.DraftRental;
import com.jwolodzko.car_rental.rental.rental.Rental;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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

    String phoneNumber;

    String company;

    @OneToMany(mappedBy = "client")
    Set<DraftRental> draftRentals;

    @OneToMany(mappedBy = "client")
    Set<Rental> rentals;
}
