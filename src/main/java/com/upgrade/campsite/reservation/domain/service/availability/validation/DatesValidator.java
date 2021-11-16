package com.upgrade.campsite.reservation.domain.service.availability.validation;

import com.upgrade.campsite.reservation.domain.service.availability.exception.InvalidDateException;
import com.upgrade.campsite.reservation.domain.service.availability.exception.InvalidDateRangeException;

import java.time.LocalDate;

public class DatesValidator {

    public static void validate(LocalDate from, LocalDate to) {
        LocalDate now = LocalDate.now();
        if (from.isBefore(now)) {
            throw new InvalidDateException(from, "Invalid from date, it must be after now");
        }
        if (to.isBefore(now)) {
            throw new InvalidDateException(to, "Invalid to date, it must be after now");
        }
        if (to.isBefore(from)) {
            throw new InvalidDateRangeException(from, to, "Invalid date range, from date must be before or equals than end date");
        }
    }
}
