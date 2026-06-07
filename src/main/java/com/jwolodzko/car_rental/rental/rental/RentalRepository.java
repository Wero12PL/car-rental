package com.jwolodzko.car_rental.rental.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("SELECT COUNT(r) FROM Rental r WHERE r.car.id = :carId " +
            "AND r.paymentStatus != 'REFUNDED' " +
            "AND r.fromDate < :toDate AND r.toDate > :fromDate")
    long countOverlappingRentals(@Param("carId") Long carId,
                                 @Param("fromDate") LocalDate fromDate,
                                 @Param("toDate") LocalDate toDate);
}
