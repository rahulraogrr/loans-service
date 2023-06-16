package com.ing.controllers;

import com.ing.dto.RateResponseDto;
import com.ing.entities.Rate;
import com.ing.mappers.RateResponseDtoMapper;
import com.ing.services.RateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RateControllerTest {

    @Test
    void getAllRates() {
        Rate rate1 = new Rate(1, "Product 1", "Rate Type 1", LocalDate.now(), 1, new BigDecimal("2.5"), LocalDateTime.now(), LocalDateTime.now());
        Rate rate2 = new Rate(2, "Product 2", "Rate Type 2", LocalDate.now(),2, new BigDecimal("3.5"), LocalDateTime.now(), LocalDateTime.now());
        List<Rate> rates = new ArrayList<>();
        rates.add(rate1);
        rates.add(rate2);

        // Mock the RateService
        RateService rateServiceMock = Mockito.mock(RateService.class);
        when(rateServiceMock.getAllRates()).thenReturn(rates);

        // Create the RateResponseDtoMapper (optional if you're not testing the mapping logic)
        RateResponseDtoMapper rateResponseDtoMapper = new RateResponseDtoMapper();

        // Create the RateController
        RateController rateController = new RateController(rateServiceMock, rateResponseDtoMapper);

        // Call the getAllRates() method
        ResponseEntity<List<RateResponseDto>> response = rateController.getAllRates();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the returned rates
        List<RateResponseDto> rateResponseDtos = response.getBody();
        assertEquals(2, rateResponseDtos.size());

        RateResponseDto rateResponseDto1 = rateResponseDtos.get(0);
        assertEquals(rate1.getProductType(), rateResponseDto1.getProductType());
        assertEquals(rate1.getRateType(), rateResponseDto1.getRateType());
        assertEquals(rate1.getInterestRate(), rateResponseDto1.getInterestRate());
        assertEquals(rate1.getMaturityPeriod(), rateResponseDto1.getMaturityPeriod());

        RateResponseDto rateResponseDto2 = rateResponseDtos.get(1);
        assertEquals(rate2.getProductType(), rateResponseDto2.getProductType());
        assertEquals(rate2.getRateType(), rateResponseDto2.getRateType());
        assertEquals(rate2.getInterestRate(), rateResponseDto2.getInterestRate());
        assertEquals(rate2.getMaturityPeriod(), rateResponseDto2.getMaturityPeriod());
    }
}