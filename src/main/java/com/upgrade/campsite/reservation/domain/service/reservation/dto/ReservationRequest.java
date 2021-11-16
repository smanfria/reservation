package com.upgrade.campsite.reservation.domain.service.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    @NotNull
    @Size(min = 2, max = 30)
    private String guestName;
    @Email
    private String guestEmail;
    @Future
    private LocalDate arrivalDate;
    @Future
    private LocalDate departureDate;
}
