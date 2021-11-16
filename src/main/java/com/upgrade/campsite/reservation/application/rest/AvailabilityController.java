package com.upgrade.campsite.reservation.application.rest;

import com.upgrade.campsite.reservation.domain.service.availability.AvailabilityService;
import com.upgrade.campsite.reservation.domain.service.availability.dto.AvailabilityResponse;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/availability")
@Value
public class AvailabilityController {
    AvailabilityService availabilityService;

    @GetMapping
    public AvailabilityResponse getAvailabilities(@RequestParam(value = "from", required = false)
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                  @RequestParam(value = "to", required = false)
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return availabilityService.getAvailabilities(from, to);
    }
}
