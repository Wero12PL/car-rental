package com.jwolodzko.car_rental.client.dto;

import java.time.LocalDate;

public record ClientRequest(String name, String surname, String address,
                            LocalDate birthDate, String email, String phoneNumber, String company) {
}
