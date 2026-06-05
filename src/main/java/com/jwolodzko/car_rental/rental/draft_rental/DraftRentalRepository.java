package com.jwolodzko.car_rental.rental.draft_rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DraftRentalRepository extends JpaRepository<DraftRental, Long> {
}
