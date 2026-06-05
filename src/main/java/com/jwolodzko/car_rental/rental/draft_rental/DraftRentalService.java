package com.jwolodzko.car_rental.rental.draft_rental;

import com.jwolodzko.car_rental.car.Car;
import com.jwolodzko.car_rental.car.CarRepository;
import com.jwolodzko.car_rental.client.Client;
import com.jwolodzko.car_rental.client.ClientRepository;
import com.jwolodzko.car_rental.exception.ResourceNotFoundException;
import com.jwolodzko.car_rental.payment.PaymentStatus;
import org.springframework.stereotype.Service;

@Service
public class DraftRentalService {

    private final DraftRentalRepository draftRentalRepository;
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;

    public DraftRentalService(DraftRentalRepository draftRentalRepository,
                              CarRepository carRepository,
                              ClientRepository clientRepository) {
        this.draftRentalRepository = draftRentalRepository;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
    }

    public void createDraftRental(DraftRentalRequest draftRentalRequest) {
        Car car = carRepository.findById(draftRentalRequest.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + draftRentalRequest.getCarId()));
        Client client = clientRepository.findById(draftRentalRequest.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + draftRentalRequest.getClientId()));

        DraftRental draftRental = new DraftRental(draftRentalRequest.getFromDate(),
                draftRentalRequest.getToDate(),
                client,
                car,
                PaymentStatus.PENDING);

        draftRentalRepository.save(draftRental);
    }
}
