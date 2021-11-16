package com.upgrade.campsite.reservation.domain.service.availability;

import com.upgrade.campsite.reservation.domain.model.Reservation;
import com.upgrade.campsite.reservation.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservation.domain.service.availability.dto.AvailabilityResponse;
import com.upgrade.campsite.reservation.domain.service.availability.validation.DatesValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class OneAvailabilityService implements AvailabilityService {
    private ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public AvailabilityResponse getAvailabilities(LocalDate from, LocalDate to) {
        LocalDate fromDate = DefaultDateCompleter.checkFromOrReturnDefault(from);
        LocalDate toDate = DefaultDateCompleter.checkToOrReturnDefault(from, to);
        DatesValidator.validate(fromDate, toDate);

        List<Reservation> reservations = reservationRepository.findReservations(fromDate, toDate);
        Set<LocalDate> reservationDates = reservations.stream().map(Reservation::getDetailedDates)
                .flatMap(List::stream).collect(Collectors.toSet());

        List<LocalDate> availabilities = fromDate.datesUntil(toDate.plusDays(1)).collect(Collectors.toList());
        availabilities.removeAll(reservationDates);
        return new AvailabilityResponse(availabilities);
    }
}
