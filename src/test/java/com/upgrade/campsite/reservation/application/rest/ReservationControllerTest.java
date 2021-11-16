package com.upgrade.campsite.reservation.application.rest;

import com.upgrade.campsite.reservation.domain.service.reservation.LimitedReservationService;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationRequest;
import com.upgrade.campsite.reservation.domain.service.reservation.dto.ReservationResponse;
import com.upgrade.campsite.reservation.util.ReservationMock;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReservationControllerTest {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LimitedReservationService reservationService;

    @Test
    public void should_create_reservation() throws Exception {
        ReservationRequest request = ReservationMock.mockRequest("Robert Foo", "r@foo.com",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
        UUID reservationCode = UUID.randomUUID();
        ReservationResponse response = ReservationMock.mockResponse(reservationCode, request);
        when(reservationService.create(request)).thenReturn(response);

        this.mockMvc.perform(post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{ " +
                                "\"guest_name\":\"" + request.getGuestName() + "\"," +
                                "\"guest_email\":\"" + request.getGuestEmail() + "\"," +
                                "\"arrival_date\":\"" + request.getArrivalDate().format(dtf) + "\"," +
                                "\"departure_date\":\"" + request.getDepartureDate().format(dtf) + "\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.guest_name", Matchers.is(response.getGuestName())))
                .andExpect(jsonPath("$.guest_email", Matchers.is(response.getGuestEmail())))
                .andExpect(jsonPath("$.arrival_date", Matchers.is(response.getArrivalDate().format(dtf))))
                .andExpect(jsonPath("$.departure_date", Matchers.is(response.getDepartureDate().format(dtf))))
                .andExpect(jsonPath("$.code", Matchers.is(response.getCode().toString())));
    }

    @Test
    public void should_not_make_reservation() throws Exception {
        this.mockMvc.perform(post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ " +
                                "\"guest_name\":\"Robert Foo\"," +
                                "\"guest_email\":\"r@foo.com\"," +
                                "\"arrival_date\":\"" + LocalDate.now().plusDays(0).format(dtf) + "\"," +
                                "\"departure_date\":\"" + LocalDate.now().plusDays(3).format(dtf) + "\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_retrieve_reservation() throws Exception {
        UUID reservationCode = UUID.randomUUID();
        ReservationResponse response = ReservationMock.mockResponse(reservationCode, "Robert Foo", "r@foo.com",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));

        when(reservationService.retrieve(reservationCode)).thenReturn(response);

        this.mockMvc.perform(get("/reservation/{code}", reservationCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guest_name", Matchers.is(response.getGuestName())))
                .andExpect(jsonPath("$.guest_email", Matchers.is(response.getGuestEmail())))
                .andExpect(jsonPath("$.arrival_date", Matchers.is(response.getArrivalDate().format(dtf))))
                .andExpect(jsonPath("$.departure_date", Matchers.is(response.getDepartureDate().format(dtf))))
                .andExpect(jsonPath("$.code", Matchers.is(reservationCode.toString())));
    }

    @Test
    public void should_modify_reservation_dates() throws Exception {
        UUID reservationCode = UUID.randomUUID();
        LocalDate arrival = LocalDate.now().plusDays(4);
        LocalDate departure = LocalDate.now().plusDays(7);
        ReservationRequest request = ReservationMock.mockRequest("Robert Foo", "r@foo.com",
                arrival, departure);
        ReservationResponse response = ReservationMock.mockResponse(reservationCode, "Robert Foo", "robert@foo.com",
                arrival, departure);

        when(reservationService.update(reservationCode, request)).thenReturn(response);

        this.mockMvc.perform(put("/reservation/{code}", reservationCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ " +
                                "\"guest_name\":\"Robert Foo\"," +
                                "\"guest_email\":\"r@foo.com\"," +
                                "\"arrival_date\":\"" + request.getArrivalDate().format(dtf) + "\"," +
                                "\"departure_date\":\"" + request.getDepartureDate().format(dtf) + "\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guest_name", Matchers.is("Robert Foo")))
                .andExpect(jsonPath("$.guest_email", Matchers.is("robert@foo.com")))
                .andExpect(jsonPath("$.arrival_date", Matchers.is(response.getArrivalDate().format(dtf))))
                .andExpect(jsonPath("$.departure_date", Matchers.is(response.getDepartureDate().format(dtf))))
                .andExpect(jsonPath("$.code", Matchers.is(reservationCode.toString())));
    }

    @Test
    public void should_remove_reservation() throws Exception {
        UUID reservationCode = UUID.randomUUID();
        this.mockMvc.perform(delete("/reservation/{code}", reservationCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}