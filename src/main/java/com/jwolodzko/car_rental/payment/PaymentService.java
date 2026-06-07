package com.jwolodzko.car_rental.payment;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    //mock payment processes, just for demo
    public PaymentStatus processPayment() {
        return new Random().nextBoolean() ? PaymentStatus.COMPLETED : PaymentStatus.FAILED; //here could be any other payment status
    }
}