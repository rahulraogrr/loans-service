package com.ing.controllers;

import com.ing.dto.MortgageCheckRequest;
import com.ing.dto.MortgageCheckResponse;
import com.ing.services.MortgageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MortgageControllerTest {

    @Test
    void checkMortgageFeasibility_Success() {
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setIncome(BigDecimal.valueOf(50000));
        request.setLoanValue(BigDecimal.valueOf(150000));
        request.setMaturityPeriod(10);

        MortgageCheckResponse response = new MortgageCheckResponse();
        response.setIsFeasible(true);


        MortgageService mortgageServiceMock = Mockito.mock(MortgageService.class);
        Mockito.when(mortgageServiceMock.performMortgageFeasibilityCheck(request)).thenReturn(response);

        MortgageController mortgageController = new MortgageController(mortgageServiceMock);
        ResponseEntity<MortgageCheckResponse> result = mortgageController.checkMortgageFeasibility(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void checkMortgageFeasibility_Failure() {
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setIncome(BigDecimal.valueOf(50000));
        request.setLoanValue(BigDecimal.valueOf(250000));
        request.setMaturityPeriod(10);

        MortgageCheckResponse response = new MortgageCheckResponse();
        response.setIsFeasible(false);
        response.setReason("Loan value cannot exceed 4 times the income");

        MortgageService mortgageServiceMock = Mockito.mock(MortgageService.class);
        Mockito.when(mortgageServiceMock.performMortgageFeasibilityCheck(request)).thenReturn(response);

        MortgageController mortgageController = new MortgageController(mortgageServiceMock);
        ResponseEntity<MortgageCheckResponse> result = mortgageController.checkMortgageFeasibility(request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
}