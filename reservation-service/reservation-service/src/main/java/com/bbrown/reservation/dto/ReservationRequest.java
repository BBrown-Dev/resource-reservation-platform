package com.bbrown.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ReservationRequest {

    @NotNull
    private UUID resourceId;

    @NotNull
    private UUID userId;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;
}