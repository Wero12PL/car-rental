package com.jwolodzko.car_rental.rental.draft_rental;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/draftRentals")
public class DraftRentalController {

    private final DraftRentalService draftRentalService;
    private final DraftRentalRepository draftRentalRepository;

    public DraftRentalController(DraftRentalService draftRentalService,
                                 DraftRentalRepository draftRentalRepository) {
        this.draftRentalService = draftRentalService;
        this.draftRentalRepository = draftRentalRepository;
    }

    @PostMapping
    public void createDraftRental(@RequestBody DraftRentalRequest draftRentalRequest) {
        draftRentalService.createDraftRental(draftRentalRequest);
    }

    @GetMapping
    public List<DraftRental> getDraftRentals() {
        return draftRentalRepository.findAll();
    }
}
