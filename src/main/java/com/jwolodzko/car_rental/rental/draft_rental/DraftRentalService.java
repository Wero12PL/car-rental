package com.jwolodzko.car_rental.rental.draft_rental;

import com.jwolodzko.car_rental.car.Car;
import com.jwolodzko.car_rental.car.CarRepository;
import com.jwolodzko.car_rental.client.Client;
import com.jwolodzko.car_rental.client.ClientRepository;
import com.jwolodzko.car_rental.exception.CarUnavailableException;
import com.jwolodzko.car_rental.exception.ResourceNotFoundException;
import com.jwolodzko.car_rental.payment.PaymentService;
import com.jwolodzko.car_rental.payment.PaymentStatus;
import com.jwolodzko.car_rental.rental.draft_rental.dto.DraftRentalRequest;
import com.jwolodzko.car_rental.rental.rental.RentalRepository;
import com.jwolodzko.car_rental.rental.rental.RentalService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class DraftRentalService {

    private final DraftRentalRepository draftRentalRepository;
    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final PaymentService paymentService;
    private final RentalService rentalService;

    public DraftRentalService(DraftRentalRepository draftRentalRepository,
                              CarRepository carRepository,
                              ClientRepository clientRepository,
                              PaymentService paymentService,
                              RentalService rentalService,
                              RentalRepository rentalRepository) {
        this.draftRentalRepository = draftRentalRepository;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.paymentService = paymentService;
        this.rentalService = rentalService;
        this.rentalRepository = rentalRepository;
    }

    public void createDraftRental(DraftRentalRequest draftRentalRequest) {
        Car car = carRepository.findByIdForUpdate(draftRentalRequest.carId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + draftRentalRequest.carId()));
        Client client = clientRepository.findById(draftRentalRequest.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + draftRentalRequest.clientId()));

        if (!draftRentalRequest.toDate().isAfter(draftRentalRequest.fromDate())) {
            throw new IllegalArgumentException("toDate must be after fromDate");
        }

        if(!isCarAvailableAtDate(car, draftRentalRequest.fromDate(), draftRentalRequest.toDate())) {
            throw new CarUnavailableException("No available cars for this date range");
        }

        DraftRental draftRental = new DraftRental(draftRentalRequest.fromDate(),
                draftRentalRequest.toDate(),
                client,
                car,
                PaymentStatus.NOT_STARTED);

        draftRentalRepository.save(draftRental);
    }

    public DraftRental processDraftRental(Long id) {
        DraftRental draftRental = draftRentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Draft rental not found with id: " + id));

        if (draftRental.getPaymentStatus() != PaymentStatus.NOT_STARTED) {
            throw new IllegalStateException("Draft rental already processed");
        }

        PaymentStatus paymentStatus = paymentService.processPayment();
        draftRental.setPaymentStatus(paymentStatus);

        if (Objects.equals(paymentStatus, PaymentStatus.COMPLETED)) {
            rentalService.createRental(draftRental);
        }

        return draftRentalRepository.save(draftRental);
    }

    private boolean isCarAvailableAtDate(Car car, LocalDate fromDate, LocalDate toDate) {
        long activeDrafts = draftRentalRepository.countOverlappingDrafts(
                car.getId(), fromDate, toDate);
        long activeRentals = rentalRepository.countOverlappingRentals(
                car.getId(), fromDate, toDate);

        return activeDrafts + activeRentals < car.getQuantity();
    }
}
