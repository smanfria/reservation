package com.upgrade.campsite.reservation.util;

import com.upgrade.campsite.reservation.domain.service.availability.dto.AvailabilityResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityMock {
    public static AvailabilityResponse mockResponse(LocalDate from, LocalDate to) {
        List<LocalDate> availableDates = from.datesUntil(to.plusDays(1)).collect(Collectors.toList());
        return new AvailabilityResponse(LocalDate.now(), availableDates);
    }
}
