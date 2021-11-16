package com.upgrade.campsite.reservation.domain.service.reservation;

import com.upgrade.campsite.reservation.domain.model.Reservation;
import com.upgrade.campsite.reservation.domain.repository.ReservationRepository;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationRequest;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationResponse;
import com.upgrade.campsite.reservation.domain.service.reservation.exception.ExpiredReservationDeleteException;
import com.upgrade.campsite.reservation.domain.service.reservation.exception.ReservationNotFoundException;
import com.upgrade.campsite.reservation.util.ReservationMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LimitedReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private LimitedReservationService limitedReservationService;

    @Test
    public void should_save_reservation() {
        ReservationRequest request = ReservationMock.mockRequest("Robert Foo", "r@foo.com",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));

        ReservationResponse response = limitedReservationService.create(request);

        Assertions.assertEquals(request.getGuestName(), response.getGuestName());
        Assertions.assertEquals(request.getGuestEmail(), response.getGuestEmail());
        Assertions.assertEquals(request.getArrivalDate(), response.getArrivalDate());
        Assertions.assertEquals(request.getDepartureDate(), response.getDepartureDate());
        Assertions.assertNotNull(response.getCode());
    }

    @Test
    public void should_not_update_reservation() {
        assertThrows(ReservationNotFoundException.class, () -> {
            UUID reservationCode = UUID.randomUUID();
            ReservationRequest request = ReservationMock.mockRequest("Robert Foo", "r@foo.com",
                    LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
            when(reservationRepository.findByCode(reservationCode)).thenReturn(Optional.empty());
            limitedReservationService.update(reservationCode, request);
        });
    }

    @Test
    public void should_find_reservation() {
        Reservation reservation = ReservationMock.mockReservation(LocalDate.now());
        when(reservationRepository.findByCode(reservation.getCode())).thenReturn(Optional.of(reservation));
        ReservationResponse response = limitedReservationService.retrieve(reservation.getCode());

        Assertions.assertEquals(reservation.getGuestName(), response.getGuestName());
        Assertions.assertEquals(reservation.getGuestEmail(), response.getGuestEmail());
        Assertions.assertEquals(reservation.getCode(), response.getCode());
        Assertions.assertEquals(reservation.getDepartureDate(), response.getDepartureDate());
        Assertions.assertEquals(reservation.getArrivalDate(), response.getArrivalDate());
    }

    @Test
    public void should_not_find_reservation() {
        assertThrows(ReservationNotFoundException.class, () -> {
            UUID reservationCode = UUID.randomUUID();
            when(reservationRepository.findByCode(reservationCode)).thenReturn(Optional.empty());
            limitedReservationService.retrieve(reservationCode);
        });
    }

    @Test
    public void should_not_delete_reservation() {
        assertThrows(ReservationNotFoundException.class, () -> {
            UUID reservationCode = UUID.randomUUID();
            when(reservationRepository.findByCode(reservationCode)).thenReturn(Optional.empty());
            limitedReservationService.delete(reservationCode);
        });
    }

    @Test
    public void should_not_delete_expired_reservation() {
        assertThrows(ExpiredReservationDeleteException.class, () -> {
            UUID reservationCode = UUID.randomUUID();
            Reservation reservation = ReservationMock.mockReservation(LocalDate.now().minusDays(7));
            when(reservationRepository.findByCode(reservationCode)).thenReturn(Optional.of(reservation));
            limitedReservationService.delete(reservationCode);
        });
    }
}