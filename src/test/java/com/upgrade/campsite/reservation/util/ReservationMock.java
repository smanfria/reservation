package com.upgrade.campsite.reservation.util;

import com.upgrade.campsite.reservation.domain.model.Reservation;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationRequest;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationMock {

    public static ReservationRequest mockRequest(String name, String email, LocalDate arrival, LocalDate departure) {
        return new ReservationRequest(name, email, arrival, departure);
    }

    public static ReservationResponse mockResponse(UUID code, ReservationRequest request) {
        return mockResponse(code, request.getGuestName(), request.getGuestEmail(),
                request.getArrivalDate(), request.getDepartureDate());
    }

    public static ReservationResponse mockResponse(UUID code, String name, String email,
                                                   LocalDate arrival, LocalDate departure) {
        return new ReservationResponse(code, name, email, arrival, departure);
    }

    public static List<Reservation> mockReservations(List<LocalDate> dates) {
        return dates.stream().map(ReservationMock::mockReservation)
                .collect(Collectors.toList());
    }

    public static Reservation mockReservation(LocalDate date) {
        return new Reservation(1L, UUID.randomUUID(), "name", "email", date, date);
    }
}
