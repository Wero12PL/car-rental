package com.jwolodzko.car_rental.rental.draft_rental;

import com.jwolodzko.car_rental.car.Car;
import com.jwolodzko.car_rental.car.CarRepository;
import com.jwolodzko.car_rental.car.CarType;
import com.jwolodzko.car_rental.client.Client;
import com.jwolodzko.car_rental.client.ClientRepository;
import com.jwolodzko.car_rental.exception.CarUnavailableException;
import com.jwolodzko.car_rental.exception.ResourceNotFoundException;
import com.jwolodzko.car_rental.location.Location;
import com.jwolodzko.car_rental.payment.PaymentService;
import com.jwolodzko.car_rental.payment.PaymentStatus;
import com.jwolodzko.car_rental.rental.draft_rental.dto.DraftRentalRequest;
import com.jwolodzko.car_rental.rental.rental.RentalRepository;
import com.jwolodzko.car_rental.rental.rental.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DraftRentalServiceTest {

    @Mock
    private DraftRentalRepository draftRentalRepository;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private RentalService rentalService;

    @InjectMocks
    private DraftRentalService draftRentalService;

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
    void createDraftRental_success() {
        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L,
                1L
        );

        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(draftRentalRepository.countOverlappingDrafts(eq(1L), any(), any())).thenReturn(0L);
        when(rentalRepository.countOverlappingRentals(eq(1L), any(), any())).thenReturn(0L);

        draftRentalService.createDraftRental(request);

        verify(draftRentalRepository).save(any(DraftRental.class));
    }

    @Test
    void createDraftRental_carNotFound() {
        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L,
                999L
        );

        when(carRepository.findByIdForUpdate(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void createDraftRental_clientNotFound() {
        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                999L,
                1L
        );

        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void createDraftRental_toDateBeforeFromDate() {
        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(1),
                1L,
                1L
        );

        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        assertThrows(IllegalArgumentException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void createDraftRental_toDateEqualsFromDate() {
        LocalDate sameDate = LocalDate.now().plusDays(1);
        DraftRentalRequest request = new DraftRentalRequest(
                sameDate,
                sameDate,
                1L,
                1L
        );

        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        assertThrows(IllegalArgumentException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void createDraftRental_carUnavailable_overlappingDrafts() {
        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L,
                1L
        );

        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(draftRentalRepository.countOverlappingDrafts(eq(1L), any(), any())).thenReturn(2L);
        when(rentalRepository.countOverlappingRentals(eq(1L), any(), any())).thenReturn(0L);

        assertThrows(CarUnavailableException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void createDraftRental_carUnavailable_overlappingRentals() {
        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L,
                1L
        );

        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(draftRentalRepository.countOverlappingDrafts(eq(1L), any(), any())).thenReturn(0L);
        when(rentalRepository.countOverlappingRentals(eq(1L), any(), any())).thenReturn(2L);

        assertThrows(CarUnavailableException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void createDraftRental_carUnavailable_mixedOverlaps() {
        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L,
                1L
        );

        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(draftRentalRepository.countOverlappingDrafts(eq(1L), any(), any())).thenReturn(1L);
        when(rentalRepository.countOverlappingRentals(eq(1L), any(), any())).thenReturn(1L);

        assertThrows(CarUnavailableException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void createDraftRental_carAvailable_oneSlotLeft() {
        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L,
                1L
        );

        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(draftRentalRepository.countOverlappingDrafts(eq(1L), any(), any())).thenReturn(1L);
        when(rentalRepository.countOverlappingRentals(eq(1L), any(), any())).thenReturn(0L);

        draftRentalService.createDraftRental(request);

        verify(draftRentalRepository).save(any(DraftRental.class));
    }

    @Test
    void processDraftRental_completed() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.NOT_STARTED
        );

        when(draftRentalRepository.findById(1L)).thenReturn(Optional.of(draft));
        when(paymentService.processPayment()).thenReturn(PaymentStatus.COMPLETED);
        when(draftRentalRepository.save(any(DraftRental.class))).thenReturn(draft);

        DraftRental result = draftRentalService.processDraftRental(1L);

        assertEquals(PaymentStatus.COMPLETED, result.getPaymentStatus());
        verify(rentalService).createRental(draft);
    }

    @Test
    void processDraftRental_failed() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.NOT_STARTED
        );

        when(draftRentalRepository.findById(1L)).thenReturn(Optional.of(draft));
        when(paymentService.processPayment()).thenReturn(PaymentStatus.FAILED);
        when(draftRentalRepository.save(any(DraftRental.class))).thenReturn(draft);

        DraftRental result = draftRentalService.processDraftRental(1L);

        assertEquals(PaymentStatus.FAILED, result.getPaymentStatus());
        verify(rentalService, never()).createRental(any());
    }

    @Test
    void processDraftRental_alreadyProcessed() {
        DraftRental draft = new DraftRental(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                client,
                car,
                PaymentStatus.NOT_STARTED
        );
        draft.setPaymentStatus(PaymentStatus.COMPLETED);

        when(draftRentalRepository.findById(1L)).thenReturn(Optional.of(draft));

        assertThrows(IllegalStateException.class, () ->
                draftRentalService.processDraftRental(1L));
    }

    @Test
    void processDraftRental_notFound() {
        when(draftRentalRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                draftRentalService.processDraftRental(999L));
    }
}
