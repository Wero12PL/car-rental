package com.jwolodzko.car_rental.rental.draft_rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DraftRentalRepository extends JpaRepository<DraftRental, Long> {
    @Query("SELECT COUNT(d) FROM DraftRental d WHERE d.car.id = :carId " +
            "AND d.paymentStatus NOT IN ('CANCELED_BY_USER', 'FAILED') " +
            "AND d.fromDate < :toDate AND d.toDate > :fromDate")
    long countOverlappingDrafts(@Param("carId") Long carId,
                                @Param("fromDate") LocalDate fromDate,
                                @Param("toDate") LocalDate toDate);
}
