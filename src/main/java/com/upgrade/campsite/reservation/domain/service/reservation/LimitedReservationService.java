package com.upgrade.campsite.reservation.domain.service.reservation;

import com.upgrade.campsite.reservation.domain.model.Reservation;
import com.upgrade.campsite.reservation.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationRequest;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationResponse;
import com.upgrade.campsite.reservation.domain.service.reservation.exception.ReservationDatesNotAvailableException;
import com.upgrade.campsite.reservation.domain.service.reservation.exception.ReservationNotFoundException;
import com.upgrade.campsite.reservation.domain.service.reservation.mapping.ReservationMapper;
import com.upgrade.campsite.reservation.domain.service.reservation.validation.ReservationValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Data
@AllArgsConstructor
public class LimitedReservationService implements ReservationService {
    private ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public ReservationResponse retrieve(UUID code) {
        Reservation reservation = this.getReservationOrThrowEx(code);
        return ReservationMapper.map(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse create(ReservationRequest request) {
        ReservationValidator.validate(request);
        this.checkAvailabilityOrThrowEx(request);
        Reservation reservation = ReservationMapper.map(UUID.randomUUID(), request);
        reservationRepository.save(reservation);
        return ReservationMapper.map(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse update(UUID code, ReservationRequest request) {
        ReservationValidator.validate(request);
        Reservation reservation = this.getReservationOrThrowEx(code);
        this.checkAvailabilityOrThrowEx(request, reservation);
        updateReservation(reservation, request);
        reservationRepository.save(reservation);
        return ReservationMapper.map(reservation);
    }

    @Override
    @Transactional
    public void delete(UUID code) {
        Reservation reservation = this.getReservationOrThrowEx(code);
        ReservationValidator.validateIsActive(reservation);
        reservationRepository.delete(reservation);
    }

    private Reservation getReservationOrThrowEx(UUID code) {
        return reservationRepository.findByCode(code).orElseThrow(ReservationNotFoundException::new);
    }

    private void updateReservation(Reservation reservation, ReservationRequest request) {
        reservation.setGuestName(request.getGuestName());
        reservation.setGuestEmail(request.getGuestEmail());
        reservation.setArrivalDate(request.getArrivalDate());
        reservation.setDepartureDate(request.getDepartureDate());
    }

    private void checkAvailabilityOrThrowEx(ReservationRequest request, Reservation storedReservation) {
        List<Reservation> reservations = reservationRepository.findReservations(request.getArrivalDate(), request.getDepartureDate());
        reservations.remove(storedReservation);
        if (!reservations.isEmpty()) {
            throw new ReservationDatesNotAvailableException();
        }
    }

    private void checkAvailabilityOrThrowEx(ReservationRequest request) {
        List<Reservation> reservations = reservationRepository.findReservations(request.getArrivalDate(), request.getDepartureDate());
        if (!reservations.isEmpty()) {
            throw new ReservationDatesNotAvailableException();
        }
    }
}
