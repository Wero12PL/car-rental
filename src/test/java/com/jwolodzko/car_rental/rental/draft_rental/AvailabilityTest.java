package com.jwolodzko.car_rental.rental.draft_rental;

import com.jwolodzko.car_rental.car.Car;
import com.jwolodzko.car_rental.car.CarRepository;
import com.jwolodzko.car_rental.car.CarType;
import com.jwolodzko.car_rental.client.Client;
import com.jwolodzko.car_rental.client.ClientRepository;
import com.jwolodzko.car_rental.exception.CarUnavailableException;
import com.jwolodzko.car_rental.location.Location;
import com.jwolodzko.car_rental.payment.PaymentService;
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
class AvailabilityTest {

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
        car.setQuantity(3);

        client = new Client();
        client.setId(1L);
        client.setName("Jan");
        client.setSurname("Kowalski");
        client.setAddress("ul. Testowa 1");
        client.setEmail("jan@test.com");
    }

    private void mockRepositories(int overlappingDrafts, int overlappingRentals) {
        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(draftRentalRepository.countOverlappingDrafts(eq(1L), any(), any()))
                .thenReturn((long) overlappingDrafts);
        when(rentalRepository.countOverlappingRentals(eq(1L), any(), any()))
                .thenReturn((long) overlappingRentals);
    }

    @Test
    void availability_noOverlaps_quantity3_allowsCreation() {
        mockRepositories(0, 0);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L, 1L
        );

        draftRentalService.createDraftRental(request);

        verify(draftRentalRepository).save(any(DraftRental.class));
    }

    @Test
    void availability_twoDrafts_quantity3_allowsCreation() {
        mockRepositories(2, 0);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L, 1L
        );

        draftRentalService.createDraftRental(request);

        verify(draftRentalRepository).save(any(DraftRental.class));
    }

    @Test
    void availability_threeDrafts_quantity3_blocksCreation() {
        mockRepositories(3, 0);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L, 1L
        );

        assertThrows(CarUnavailableException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void availability_oneDraftTwoRentals_quantity3_blocksCreation() {
        mockRepositories(1, 2);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L, 1L
        );

        assertThrows(CarUnavailableException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void availability_differentDates_allowsCreation() {
        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        when(draftRentalRepository.countOverlappingDrafts(
                eq(1L),
                eq(LocalDate.now().plusDays(10)),
                eq(LocalDate.now().plusDays(15))))
                .thenReturn(0L);
        when(rentalRepository.countOverlappingRentals(
                eq(1L),
                eq(LocalDate.now().plusDays(10)),
                eq(LocalDate.now().plusDays(15))))
                .thenReturn(0L);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(15),
                1L, 1L
        );

        draftRentalService.createDraftRental(request);

        verify(draftRentalRepository).save(any(DraftRental.class));
    }

    @Test
    void availability_overlappingStart_allBlocks() {
        mockRepositories(3, 0);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(3),
                LocalDate.now().plusDays(7),
                1L, 1L
        );

        assertThrows(CarUnavailableException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void availability_overlappingEnd_allBlocks() {
        mockRepositories(3, 0);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(0),
                LocalDate.now().plusDays(3),
                1L, 1L
        );

        assertThrows(CarUnavailableException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void availability_exactSameDates_blocksCreation() {
        mockRepositories(3, 0);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L, 1L
        );

        assertThrows(CarUnavailableException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void availability_consecutiveDates_allowsCreation() {
        when(carRepository.findByIdForUpdate(1L)).thenReturn(Optional.of(car));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(draftRentalRepository.countOverlappingDrafts(
                eq(1L),
                eq(LocalDate.now().plusDays(6)),
                eq(LocalDate.now().plusDays(10))))
                .thenReturn(0L);
        when(rentalRepository.countOverlappingRentals(
                eq(1L),
                eq(LocalDate.now().plusDays(6)),
                eq(LocalDate.now().plusDays(10))))
                .thenReturn(0L);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(6),
                LocalDate.now().plusDays(10),
                1L, 1L
        );

        draftRentalService.createDraftRental(request);

        verify(draftRentalRepository).save(any(DraftRental.class));
    }

    @Test
    void availability_canceledDraftsNotCounted() {
        mockRepositories(0, 0);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L, 1L
        );

        draftRentalService.createDraftRental(request);

        verify(draftRentalRepository).save(any(DraftRental.class));
    }

    @Test
    void availability_refundedRentalsNotCounted() {
        mockRepositories(0, 0);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L, 1L
        );

        draftRentalService.createDraftRental(request);

        verify(draftRentalRepository).save(any(DraftRental.class));
    }

    @Test
    void availability_quantity1_oneDraft_blocksCreation() {
        car.setQuantity(1);
        mockRepositories(1, 0);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L, 1L
        );

        assertThrows(CarUnavailableException.class, () ->
                draftRentalService.createDraftRental(request));
    }

    @Test
    void availability_quantity1_noDrafts_allowsCreation() {
        car.setQuantity(1);
        mockRepositories(0, 0);

        DraftRentalRequest request = new DraftRentalRequest(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L, 1L
        );

        draftRentalService.createDraftRental(request);

        verify(draftRentalRepository).save(any(DraftRental.class));
    }
}
