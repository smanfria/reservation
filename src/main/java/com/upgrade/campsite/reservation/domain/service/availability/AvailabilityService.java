package com.upgrade.campsite.reservation.domain.service.availability;

import com.upgrade.campsite.reservation.domain.service.availability.dto.AvailabilityResponse;

import java.time.LocalDate;

public interface AvailabilityService {
    AvailabilityResponse getAvailabilities(LocalDate from, LocalDate to);
}
