package com.upgrade.campsite.reservation.domain.service.availability.exception;

import java.time.LocalDate;

public class InvalidDateRangeException extends IllegalArgumentException {
    private LocalDate from;
    private LocalDate to;

    public InvalidDateRangeException(LocalDate from, LocalDate to, String msg) {
        super(msg);
        this.from = from;
        this.to = to;
    }
}
