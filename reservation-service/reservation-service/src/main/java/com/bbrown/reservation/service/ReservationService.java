package com.bbrown.reservation.service;

import com.bbrown.reservation.events.ReservationCreatedEvent;
import com.bbrown.reservation.events.ReservationEventProducer;
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

    private final ReservationRepository repository;
    private final ReservationEventProducer eventProducer;

    /**
     * Creates a new reservation, validates business rules,
     * checks for conflicts, saves it, and publishes a Kafka event.
     */
    public Reservation create(Reservation reservation) {

        // 1. Validate start < end
        if (!reservation.getStartTime().isBefore(reservation.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before endTime");
        }

        // 2. Validate start time is in the future
        if (reservation.getStartTime().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Start time must be in the future");
        }

        // 3. Check for overlapping reservations
        boolean conflict = repository.existsByResourceIdAndStartTimeLessThanAndEndTimeGreaterThan(
                reservation.getResourceId(),
                reservation.getEndTime(),
                reservation.getStartTime()
        );

        if (conflict) {
            throw new IllegalStateException("Reservation conflicts with an existing booking");
        }

        // 4. Set initial metadata
        reservation.setStatus(ReservationStatus.CREATED);
        reservation.setCreatedAt(Instant.now());
        reservation.setUpdatedAt(Instant.now());

        // 5. Save to database
        Reservation saved = repository.save(reservation);

        // 6. Build event payload
        ReservationCreatedEvent event = ReservationCreatedEvent.builder()
                .reservationId(saved.getId())
                .resourceId(saved.getResourceId())
                .userId(saved.getUserId())
                .startTime(saved.getStartTime())
                .endTime(saved.getEndTime())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .build();

        // 7. Publish event to Kafka
        eventProducer.publishReservationCreatedEvent(event);

        // 8. Return saved reservation
        return saved;
    }

    /**
     * Retrieves a reservation by ID.
     */
    public Reservation get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }
}