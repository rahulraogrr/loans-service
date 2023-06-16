package com.ing.services.helpers;

import com.ing.dto.MortgageCheckRequest;
import com.ing.dto.MortgageCheckResponse;
import com.ing.entities.Rate;
import com.ing.repositories.RateRepository;
import com.ing.rules.impl.MortgageFeasibilityRule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.ing.constants.MortgageFeasibilityConstants.LOAN_VALUE_EXCEED_CHECK;
import static com.ing.constants.MortgageFeasibilityConstants.LOAN_VALUE_GREATER_THAN_HOME_VALUE;
import static org.junit.jupiter.api.Assertions.*;

class MortgageHelperTest {

    @Test
    void checkFeasibilityWhenLoanValueExceeds4TimesIncome() {
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setIncome(BigDecimal.valueOf(50000));
        request.setLoanValue(BigDecimal.valueOf(250000));
        request.setMaturityPeriod(10);

        Rate rate = new Rate();
        rate.setProductType("Mortgage");
        rate.setRateType("Fixed");
        rate.setMaturityPeriod(10);
        rate.setInterestRate(BigDecimal.valueOf(3.5));

        List<Rate> rates = Collections.singletonList(rate);

        RateRepository rateRepositoryMock = Mockito.mock(RateRepository.class);
        Mockito.when(rateRepositoryMock.findByProductTypeAndRateType(Mockito.anyString(), Mockito.anyString())).thenReturn(rates);

        MortgageFeasibilityRule mortgageFeasibilityRuleMock = Mockito.mock(MortgageFeasibilityRule.class);
        Mockito.when(mortgageFeasibilityRuleMock.evaluateCondition(request, LOAN_VALUE_EXCEED_CHECK)).thenReturn(true);
        Mockito.when(mortgageFeasibilityRuleMock.evaluateCondition(request, LOAN_VALUE_GREATER_THAN_HOME_VALUE)).thenReturn(false);

        MortgageHelper mortgageHelper = new MortgageHelper(rateRepositoryMock, mortgageFeasibilityRuleMock);
        MortgageCheckResponse response = mortgageHelper.checkFeasibility(request);

        assertFalse(response.getIsFeasible());
        assertEquals("Loan value cannot exceed 4 times the income", response.getReason());
    }

    @Test
    void checkFeasibilityInLimits(){
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setIncome(BigDecimal.valueOf(50000));
        request.setLoanValue(BigDecimal.valueOf(150000));
        request.setMaturityPeriod(10);
        request.setHomeValue(BigDecimal.valueOf(160000));

        Rate rate = new Rate();
        rate.setProductType("Mortgage");
        rate.setRateType("Fixed");
        rate.setEffectiveDate(LocalDate.now());
        rate.setMaturityPeriod(10);
        rate.setInterestRate(BigDecimal.valueOf(3.5));

        List<Rate> rates = Collections.singletonList(rate);

        RateRepository rateRepositoryMock = Mockito.mock(RateRepository.class);
        Mockito.when(rateRepositoryMock
                .findByProductTypeAndRateTypeAndEffectiveDateLessThanEqualAndMaturityPeriodOrderByEffectiveDateDesc(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(rates);

        MortgageFeasibilityRule mortgageFeasibilityRuleMock = Mockito.mock(MortgageFeasibilityRule.class);
        Mockito.when(mortgageFeasibilityRuleMock.evaluateCondition(request, LOAN_VALUE_EXCEED_CHECK)).thenReturn(false);
        Mockito.when(mortgageFeasibilityRuleMock.evaluateCondition(request, LOAN_VALUE_GREATER_THAN_HOME_VALUE)).thenReturn(false);

        MortgageHelper mortgageHelper = new MortgageHelper(rateRepositoryMock, mortgageFeasibilityRuleMock);
        MortgageCheckResponse response = mortgageHelper.checkFeasibility(request);

        assertTrue(response.getIsFeasible());
        assertNull(response.getReason());
    }
}