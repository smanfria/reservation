package com.upgrade.campsite.reservation.domain.service.reservation.exception;

public class ExpiredReservationDeleteException extends RuntimeException {
    public ExpiredReservationDeleteException() {
        super("could not delete an expired reservation");
    }

}
