package com.upgrade.campsite.reservation.domain.service.availability.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityResponse {
    private LocalDate date;
    private List<LocalDate> availabilities;

    public AvailabilityResponse(List<LocalDate> availabilities) {
        this(LocalDate.now(), availabilities);
    }
}
