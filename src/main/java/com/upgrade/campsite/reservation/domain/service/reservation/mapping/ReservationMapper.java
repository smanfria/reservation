package com.upgrade.campsite.reservation.domain.service.reservation.mapping;

import com.upgrade.campsite.reservation.domain.model.Reservation;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationRequest;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationResponse;

import java.util.UUID;

public class ReservationMapper {
    public static ReservationResponse map(Reservation reservation) {
        return new ReservationResponse(reservation.getCode(), reservation.getGuestName(), reservation.getGuestEmail(),
                reservation.getArrivalDate(), reservation.getDepartureDate());
    }

    public static Reservation map(UUID uuid, ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setCode(uuid);
        reservation.setGuestName(request.getGuestName());
        reservation.setGuestEmail(request.getGuestEmail());
        reservation.setDepartureDate(request.getDepartureDate());
        reservation.setArrivalDate(request.getArrivalDate());
        return reservation;
    }

}
