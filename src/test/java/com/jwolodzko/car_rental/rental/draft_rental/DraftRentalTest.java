package com.jwolodzko.car_rental.rental.draft_rental;

import com.jwolodzko.car_rental.car.Car;
import com.jwolodzko.car_rental.car.CarType;
import com.jwolodzko.car_rental.client.Client;
import com.jwolodzko.car_rental.location.Location;
import com.jwolodzko.car_rental.payment.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DraftRentalTest {

    private Car car;
    private Client client;

    @BeforeEach
    void setUp() {
        Location location = new Location();
        location.setId(1L);
        location.setName("Warsaw Branch");
        location.setCity("Warsaw");
        location.setCountry("Poland");

        car = new Car();
        car.setId(1L);
        car.setBrand("Toyota");
        car.setModelName("Camry");
        car.setCarType(CarType.Sedan);
        car.setPrice(100);
        car.setLocation(location);
        car.setQuantity(2);

        client = new Client();
        client.setId(1L);
        client.setName("Jan");
        client.setSurname("Kowalski");
        client.setAddress("ul. Testowa 1");
        client.setEmail("jan@test.com");
    }

    @Test
    void draftRental_createdWithNotStarted() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.NOT_STARTED
        );

        assertEquals(PaymentStatus.NOT_STARTED, draft.getPaymentStatus());
    }

    @Test
    void draftRental_createdWithPending() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.PENDING
        );

        assertEquals(PaymentStatus.PENDING, draft.getPaymentStatus());
    }

    @Test
    void draftRental_createdWithFailed() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.FAILED
        );

        assertEquals(PaymentStatus.FAILED, draft.getPaymentStatus());
    }

    @Test
    void draftRental_rejectsCompleted() {
        assertThrows(IllegalArgumentException.class, () ->
                new DraftRental(
                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(5),
                        client,
                        car,
                        PaymentStatus.COMPLETED
                ));
    }

    @Test
    void draftRental_rejectsRefunded() {
        assertThrows(IllegalArgumentException.class, () ->
                new DraftRental(
                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(5),
                        client,
                        car,
                        PaymentStatus.REFUNDED
                ));
    }

    @Test
    void draftRental_fieldsAreSet() {
        LocalDate fromDate = LocalDate.now().plusDays(1);
        LocalDate toDate = LocalDate.now().plusDays(5);

        DraftRental draft = new DraftRental(
                fromDate,
                toDate,
                client,
                car,
                PaymentStatus.NOT_STARTED
        );

        assertEquals(fromDate, draft.getFromDate());
        assertEquals(toDate, draft.getToDate());
        assertEquals(client, draft.getClient());
        assertEquals(car, draft.getCar());
    }
}
