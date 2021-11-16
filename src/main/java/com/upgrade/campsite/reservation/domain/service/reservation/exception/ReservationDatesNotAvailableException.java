package com.upgrade.campsite.reservation.domain.service.reservation.exception;

public class ReservationDatesNotAvailableException extends RuntimeException {

    public ReservationDatesNotAvailableException() {
        super("reservation dates not available");
    }
}
