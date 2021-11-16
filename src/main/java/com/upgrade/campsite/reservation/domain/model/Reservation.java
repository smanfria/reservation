package com.upgrade.campsite.reservation.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "uuid-char")
    @Column(name = "code", nullable = false, unique = true)
    private UUID code;

    @Column(name = "guest_name", nullable = false, length = 50)
    private String guestName;

    @Column(name = "email", nullable = false, length = 50)
    private String guestEmail;

    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    public List<LocalDate> getDetailedDates() {
        return arrivalDate.datesUntil(departureDate.plusDays(1)).collect(Collectors.toList());
    }
}
