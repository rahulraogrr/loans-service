package com.ing.rules.impl;

import com.ing.dto.MortgageCheckRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.ing.constants.MortgageFeasibilityConstants.LOAN_VALUE_EXCEED_CHECK;
import static com.ing.constants.MortgageFeasibilityConstants.LOAN_VALUE_GREATER_THAN_HOME_VALUE;
import static org.junit.jupiter.api.Assertions.*;

class MortgageFeasibilityRuleTest {

    @Test
    void whenLOAN_VALUE_EXCEED_CHECK() {
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setIncome(BigDecimal.valueOf(50000));
        request.setLoanValue(BigDecimal.valueOf(250000));
        boolean result = new MortgageFeasibilityRule().evaluateCondition(request, LOAN_VALUE_EXCEED_CHECK);
        assertTrue(result);
    }

    @Test
    void whenLOAN_VALUE_NOT_EXCEED_CHECK() {
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setIncome(BigDecimal.valueOf(50000));
        request.setLoanValue(BigDecimal.valueOf(150000));
        boolean result = new MortgageFeasibilityRule().evaluateCondition(request, LOAN_VALUE_EXCEED_CHECK);
        assertFalse(result);
    }

    @Test
    void whenLOAN_VALUE_GREATER_THAN_HOME_VALUE(){
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setLoanValue(BigDecimal.valueOf(300000));
        request.setHomeValue(BigDecimal.valueOf(250000));
        boolean result = new MortgageFeasibilityRule().evaluateCondition(request, LOAN_VALUE_GREATER_THAN_HOME_VALUE);
        assertTrue(result);
    }

    @Test
    void whenLOAN_VALUE_NOT_GREATER_THAN_HOME_VALUE(){
        MortgageCheckRequest request = new MortgageCheckRequest();
        request.setLoanValue(BigDecimal.valueOf(200000));
        request.setHomeValue(BigDecimal.valueOf(250000));
        boolean result = new MortgageFeasibilityRule().evaluateCondition(request, LOAN_VALUE_GREATER_THAN_HOME_VALUE);
        assertFalse(result);
    }
}