package com.upgrade.campsite.reservation.domain.service.availability;

import java.time.LocalDate;

public class DefaultDateCompleter {
    public static LocalDate checkFromOrReturnDefault(LocalDate from) {
        if (from == null) {
            return LocalDate.now().plusDays(1);
        }
        return from;
    }

    public static LocalDate checkToOrReturnDefault(LocalDate from, LocalDate to) {
        if (to == null) {
            return from.plusMonths(1);
        }
        return to;
    }
}
