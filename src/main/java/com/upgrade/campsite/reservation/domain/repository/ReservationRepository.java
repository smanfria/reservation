package com.upgrade.campsite.reservation.domain.repository;

import com.upgrade.campsite.reservation.domain.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    List<Reservation> findReservations(LocalDate from, LocalDate to);

    Optional<Reservation> findByCode(UUID code);

    Reservation save(Reservation reservation);

    void delete(Reservation reservation);
}
