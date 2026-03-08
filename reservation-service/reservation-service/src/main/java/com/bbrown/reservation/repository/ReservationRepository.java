package com.bbrown.reservation.repository;

import com.bbrown.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    // Checks if any existing reservation overlaps the requested time window
    boolean existsByResourceIdAndStartTimeLessThanAndEndTimeGreaterThan(
            UUID resourceId,
            Instant endTime,
            Instant startTime
    );
}
