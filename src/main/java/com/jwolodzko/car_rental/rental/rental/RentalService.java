package com.jwolodzko.car_rental.rental.rental;

import com.jwolodzko.car_rental.rental.draft_rental.DraftRental;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository){
        this.rentalRepository = rentalRepository;
    }

    public void createRental(DraftRental draftRental) {
        rentalRepository.save(new Rental(draftRental));
    }
}
