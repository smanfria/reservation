package com.upgrade.campsite.reservation.application.rest;

import com.upgrade.campsite.reservation.domain.service.availability.OneAvailabilityService;
import com.upgrade.campsite.reservation.domain.service.availability.dto.AvailabilityResponse;
import com.upgrade.campsite.reservation.util.AvailabilityMock;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AvailabilityController.class)
@AutoConfigureMockMvc(addFilters = false)
class AvailabilityControllerTest {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OneAvailabilityService oneAvailabilityService;

    @Test
    public void should_retrieve_availabilities() throws Exception {
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);

        AvailabilityResponse response = AvailabilityMock.mockResponse(from, to);
        when(oneAvailabilityService.getAvailabilities(from, to)).thenReturn(response);

        this.mockMvc.perform(get("/availability")
                        .param("from", from.format(dtf))
                        .param("to", to.format(dtf))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", Matchers.is(LocalDate.now().format(dtf))))
                .andExpect(jsonPath("$.availabilities", hasSize(2)))
                .andExpect(jsonPath("$.availabilities[0]", Matchers.is(from.format(dtf))))
                .andExpect(jsonPath("$.availabilities[1]", Matchers.is(to.format(dtf))));
    }
}