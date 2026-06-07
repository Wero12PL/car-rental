package com.jwolodzko.car_rental.rental.rental;

import com.jwolodzko.car_rental.car.Car;
import com.jwolodzko.car_rental.car.CarType;
import com.jwolodzko.car_rental.client.Client;
import com.jwolodzko.car_rental.location.Location;
import com.jwolodzko.car_rental.payment.PaymentStatus;
import com.jwolodzko.car_rental.rental.draft_rental.DraftRental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private RentalService rentalService;

    private Car car;
    private Client client;
    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
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
    void createRental_fromCompletedDraft() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.NOT_STARTED
        );
        draft.setPaymentStatus(PaymentStatus.COMPLETED);

        rentalService.createRental(draft);

        verify(rentalRepository).save(any(Rental.class));
    }

    @Test
    void createRental_fromRefundedDraft() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.NOT_STARTED
        );
        draft.setPaymentStatus(PaymentStatus.REFUNDED);

        rentalService.createRental(draft);

        verify(rentalRepository).save(any(Rental.class));
    }

    @Test
    void rentalConstructor_rejectsNotStarted() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.NOT_STARTED
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Rental(draft));
    }

    @Test
    void rentalConstructor_rejectsPending() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.PENDING
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Rental(draft));
    }

    @Test
    void rentalConstructor_rejectsFailed() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.FAILED
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Rental(draft));
    }

    @Test
    void rentalConstructor_acceptsCompleted() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.NOT_STARTED
        );
        draft.setPaymentStatus(PaymentStatus.COMPLETED);

        Rental rental = new Rental(draft);

        assertEquals(draft.getFromDate(), rental.getFromDate());
        assertEquals(draft.getToDate(), rental.getToDate());
        assertEquals(draft.getClient(), rental.getClient());
        assertEquals(draft.getCar(), rental.getCar());
        assertEquals(PaymentStatus.COMPLETED, rental.getPaymentStatus());
    }

    @Test
    void rentalConstructor_acceptsRefunded() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.NOT_STARTED
        );
        draft.setPaymentStatus(PaymentStatus.REFUNDED);

        Rental rental = new Rental(draft);

        assertEquals(PaymentStatus.REFUNDED, rental.getPaymentStatus());
    }
}
