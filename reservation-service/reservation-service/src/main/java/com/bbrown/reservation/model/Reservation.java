package com.bbrown.reservation.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Reservation {
    @Id
    @GeneratedValue
    private UUID id;

    private UUID resourceId;
    private UUID userId;

    private Instant startTime;
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
