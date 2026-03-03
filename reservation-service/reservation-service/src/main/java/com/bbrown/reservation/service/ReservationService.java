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

    private final ReservationRepository repository;

    public Reservation create(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CREATED);
        reservation.setCreatedAt(Instant.now());
        reservation.setUpdatedAt(Instant.now());
        return repository.save(reservation);
    }

    public Reservation get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }
}
