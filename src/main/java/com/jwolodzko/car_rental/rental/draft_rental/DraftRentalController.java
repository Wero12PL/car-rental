package com.jwolodzko.car_rental.rental.draft_rental;

import com.jwolodzko.car_rental.rental.draft_rental.dto.DraftRentalRequest;
import com.jwolodzko.car_rental.rental.draft_rental.dto.DraftRentalResponse;
import jakarta.validation.Valid;
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
    public void createDraftRental(@Valid @RequestBody DraftRentalRequest draftRentalRequest) {
        draftRentalService.createDraftRental(draftRentalRequest);
    }

    @GetMapping
    public List<DraftRentalResponse> getDraftRentals() {
        return draftRentalRepository.findAll().stream()
                .map(DraftRentalResponse::new)
                .toList();
    }

    @PostMapping("/{id}/process")
    public DraftRental processDraftRental(@PathVariable Long id) {
        return draftRentalService.processDraftRental(id);
    }
}
