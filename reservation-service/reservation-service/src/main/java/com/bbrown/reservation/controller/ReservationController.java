package com.bbrown.reservation.controller;

import com.bbrown.reservation.dto.ReservationRequest;
import com.bbrown.reservation.dto.ReservationResponse;
import com.bbrown.reservation.mapper.ReservationMapper;
import com.bbrown.reservation.model.Reservation;
import com.bbrown.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;
    private final ReservationMapper mapper;

    @PostMapping
    public ReservationResponse create(@RequestBody @Valid ReservationRequest request) {

        // Convert the incoming DTO into a Reservation entity
        Reservation reservation = mapper.toEntity(request);

        // Pass the entity into the service layer
        Reservation saved = service.create(reservation);

        // Convert the saved entity back into a response DTO
        return mapper.toResponse(saved);
    }

    @GetMapping("/{id}")
    public ReservationResponse get(@PathVariable UUID id) {
        return mapper.toResponse(service.get(id));
    }

}
