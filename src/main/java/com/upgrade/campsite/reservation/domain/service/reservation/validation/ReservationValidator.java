package com.upgrade.campsite.reservation.domain.service.reservation.validation;

import com.upgrade.campsite.reservation.domain.model.Reservation;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationRequest;
import com.upgrade.campsite.reservation.domain.service.reservation.exception.ExpiredReservationDeleteException;
import com.upgrade.campsite.reservation.domain.service.reservation.exception.MaxReservationDaysExceededException;

import java.time.LocalDate;

public class ReservationValidator {
    private static final long MAX_RESERVATION_DAYS = 3;

    public static void validate(ReservationRequest request) {
        LocalDate arrivalDate = request.getArrivalDate();
        LocalDate departureDate = request.getDepartureDate();

        if (arrivalDate.datesUntil(departureDate.plusDays(1)).count() > MAX_RESERVATION_DAYS) {
            throw new MaxReservationDaysExceededException();
        }
    }

    public static void validateIsActive(Reservation reservation) {
        if (reservation.getDepartureDate().isBefore(LocalDate.now())) {
            throw new ExpiredReservationDeleteException();
        }
    }
}
