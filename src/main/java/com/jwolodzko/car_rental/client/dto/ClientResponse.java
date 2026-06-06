package com.jwolodzko.car_rental.client.dto;

import com.jwolodzko.car_rental.client.Client;

import java.time.LocalDate;

public record ClientResponse(Long id, String name, String surname, String address,
                             LocalDate birthDate, String email, String phoneNumber, String company) {
    public ClientResponse(Client client) {
        this(client.getId(), client.getName(), client.getSurname(), client.getAddress(),
                client.getBirthDate(), client.getEmail(), client.getPhoneNumber(), client.getCompany());
    }
}
