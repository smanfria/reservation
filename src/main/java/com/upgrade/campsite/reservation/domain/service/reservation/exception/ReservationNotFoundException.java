package com.upgrade.campsite.reservation.domain.service.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException() {
        super("reservation not found");
    }
}
