package com.bbrown.reservation.events;
import com.bbrown.reservation.model.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * This class represents the event that will be published to Kafka
 * whenever a new reservation is successfully created.
 */
@Data
@Builder
public class ReservationCreatedEvent {
    // Unique ID of the reservation
    private UUID reservationId;

    // ID of the resource being reserved
    private UUID resourceId;

    // ID of the user who made the reservation
    private UUID userId;

    // Start and end times of the reservation
    private Instant startTime;
    private Instant endTime;

    // Status of the reservation (e.g., CREATED)
    private ReservationStatus status;

    // Timestamp when the reservation was created
    private Instant createdAt;

}
