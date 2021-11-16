package com.upgrade.campsite.reservation.domain.service.availability.exception;

import java.time.LocalDate;

public class InvalidDateException extends IllegalArgumentException {
    private LocalDate from;

    public InvalidDateException(LocalDate date, String msg) {
        super(msg);
        this.from = date;
    }
}
