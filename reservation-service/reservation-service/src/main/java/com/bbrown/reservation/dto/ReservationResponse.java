package com.bbrown.reservation.dto;

import com.bbrown.reservation.model.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class ReservationResponse {

    private UUID id;
    private UUID resourceId;
    private UUID userId;
    private Instant startTime;
    private Instant endTime;
    private ReservationStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}