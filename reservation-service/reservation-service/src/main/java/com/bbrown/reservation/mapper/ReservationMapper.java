package com.bbrown.reservation.mapper;

import com.bbrown.reservation.dto.ReservationRequest;
import com.bbrown.reservation.dto.ReservationResponse;
import com.bbrown.reservation.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    // Converts a ReservationRequest DTO into a Reservation entity
    public Reservation toEntity(ReservationRequest request) {
        return Reservation.builder()
                .resourceId(request.getResourceId())
                .userId(request.getUserId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }

    // Converts a Reservation entity into a ReservationResponse DTO
    public ReservationResponse toResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .resourceId(reservation.getResourceId())
                .userId(reservation.getUserId())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .updatedAt(reservation.getUpdatedAt())
                .build();
    }
}
