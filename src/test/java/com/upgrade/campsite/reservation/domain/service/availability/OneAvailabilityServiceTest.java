package com.upgrade.campsite.reservation.domain.service.availability;

import com.upgrade.campsite.reservation.domain.model.Reservation;
import com.upgrade.campsite.reservation.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservation.domain.service.availability.dto.AvailabilityResponse;
import com.upgrade.campsite.reservation.util.ReservationMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OneAvailabilityServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private OneAvailabilityService oneAvailabilityService;


    @Test
    void should_retrieve_all_availabilities() {
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        List<Reservation> reservations = Collections.emptyList();

        when(reservationRepository.findReservations(from, to)).thenReturn(reservations);

        AvailabilityResponse availabilities = oneAvailabilityService.getAvailabilities(from, to);

        Assertions.assertEquals(2, availabilities.getAvailabilities().size());
        Assertions.assertEquals(from, availabilities.getAvailabilities().get(0));
        Assertions.assertEquals(to, availabilities.getAvailabilities().get(1));
        Assertions.assertEquals(LocalDate.now(), availabilities.getDate());

        verify(reservationRepository, times(1)).findReservations(from, to);
    }

    @Test
    void should_not_retrieve_availabilities() {
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        List<Reservation> reservations = ReservationMock.mockReservations(List.of(from, to));

        when(reservationRepository.findReservations(from, to)).thenReturn(reservations);

        AvailabilityResponse availabilities = oneAvailabilityService.getAvailabilities(from, to);

        Assertions.assertEquals(0, availabilities.getAvailabilities().size());
        Assertions.assertEquals(LocalDate.now(), availabilities.getDate());

        verify(reservationRepository, times(1)).findReservations(from, to);
    }
}