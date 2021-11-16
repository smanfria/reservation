package com.upgrade.campsite.reservation.domain.service.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private UUID code;
    private String guestName;
    private String guestEmail;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
}
