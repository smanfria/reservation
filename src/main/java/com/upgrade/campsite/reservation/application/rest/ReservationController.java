package com.upgrade.campsite.reservation.application.rest;

import com.upgrade.campsite.reservation.domain.service.reservation.ReservationService;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationRequest;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationResponse;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
@Value
public class ReservationController {
    ReservationService reservationService;

    @GetMapping("/{code}")
    public ReservationResponse retrieveReservation(@PathVariable UUID code) {
        return reservationService.retrieve(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse makeReservation(@Valid @RequestBody ReservationRequest reservation) {
        return reservationService.create(reservation);
    }

    @PutMapping("/{code}")
    public ReservationResponse modifyReservation(@PathVariable UUID code,
                                                 @Valid @RequestBody ReservationRequest reservation) {
        return reservationService.update(code, reservation);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    public void removeReservation(@PathVariable UUID code) {
        reservationService.delete(code);
    }
}
