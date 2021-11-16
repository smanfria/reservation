package com.upgrade.campsite.reservation.infrastructure.repository;

import com.upgrade.campsite.reservation.domain.model.Reservation;
import com.upgrade.campsite.reservation.domain.repository.ReservationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface JPAReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepository {

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "100")})
    @Query("select r from Reservation r "
            + "where ((r.arrivalDate < ?1 and ?2 < r.departureDate) "
            + "or (?1 < r.departureDate and r.departureDate <= ?2) "
            + "or (?1 <= r.departureDate and r.departureDate <=?2)) "
            + "order by r.departureDate asc")
    List<Reservation> findReservations(LocalDate from, LocalDate to);
}
