package com.bbrown.reservation.service;

import com.bbrown.reservation.model.Reservation;
import com.bbrown.reservation.model.ReservationStatus;
import com.bbrown.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    // Inject the ReservationRepository so we can query and save reservations
    private final ReservationRepository repository;

    /**
     * Creates a new reservation after validating business rules
     */
    public Reservation create(Reservation reservation) {

        // Validate that startTime is before endTime
        if (!reservation.getStartTime().isBefore(reservation.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before endTime");
        }

        // Validate that the reservation starts in the future
        if (reservation.getStartTime().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Start time must be in the future");
        }

        // Check for overlapping reservations for the same resource
        boolean conflict = repository.existsByResourceIdAndStartTimeLessThanAndEndTimeGreaterThan(
                reservation.getResourceId(),   // resource being reserved
                reservation.getEndTime(),      // requested end time
                reservation.getStartTime()     // requested start time
        );

        // If a conflict exists, return a 409 Conflict response
        if (conflict) {
            throw new IllegalStateException("Reservation conflicts with an existing booking");
        }

        // Set initial status and timestamps
        reservation.setStatus(ReservationStatus.CREATED);
        reservation.setCreatedAt(Instant.now());
        reservation.setUpdatedAt(Instant.now());

        // Save the reservation to the database
        return repository.save(reservation);
    }

    /**
     * Retrieves a reservation by ID
     */
    public Reservation get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }
}