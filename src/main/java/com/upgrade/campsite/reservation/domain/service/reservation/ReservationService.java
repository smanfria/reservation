package com.upgrade.campsite.reservation.domain.service.reservation;

import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationRequest;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationResponse;

import java.util.UUID;

public interface ReservationService {
    ReservationResponse retrieve(UUID code);

    ReservationResponse create(ReservationRequest reservation);

    ReservationResponse update(UUID code, ReservationRequest reservation);

    void delete(UUID code);
}
