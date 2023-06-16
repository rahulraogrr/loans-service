package com.ing.rules.impl;

import com.ing.dto.MortgageCheckRequest;
import com.ing.rules.Rule;
import org.springframework.stereotype.Component;

import static com.ing.constants.MortgageFeasibilityConstants.*;

@Component
public class MortgageFeasibilityRule implements Rule<MortgageCheckRequest> {

    public static final int MAX_INCOME_MULTIPLIER = 4;

    @Override
    public boolean evaluateCondition(MortgageCheckRequest request, String condition) {
        boolean passed = false;
        switch (condition) {
            case LOAN_VALUE_EXCEED_CHECK -> {
                double maxIncomeMortgage = request.getIncome().doubleValue() * MAX_INCOME_MULTIPLIER;
                if (request.getLoanValue().doubleValue() > maxIncomeMortgage) passed = true;
            }
            case LOAN_VALUE_GREATER_THAN_HOME_VALUE -> {
                if (request.getLoanValue().compareTo(request.getHomeValue()) > 0) passed = true;
            }
        }
        return passed;
    }

}
