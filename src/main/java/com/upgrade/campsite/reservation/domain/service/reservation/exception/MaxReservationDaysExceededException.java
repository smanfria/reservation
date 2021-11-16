package com.upgrade.campsite.reservation.domain.service.reservation.exception;

public class MaxReservationDaysExceededException extends RuntimeException {

    public MaxReservationDaysExceededException() {
        super("max reservation days exceeded");
    }
}
