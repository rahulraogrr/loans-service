package com.ing.services.validation;

import com.ing.dto.MortgageCheckRequest;
import com.ing.exceptions.custom.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MortgageValidation {

    /**
     * Validate business rules and incoming request
     * @param request {@link MortgageCheckRequest}
     * @return List of validation errors
     */
    public void validate(MortgageCheckRequest request) {
        List<String> errors = new ArrayList<>();

        BigDecimal income = getOrDefault(request.getIncome(), BigDecimal.ZERO);
        BigDecimal loanValue = getOrDefault(request.getLoanValue(), BigDecimal.ZERO);
        BigDecimal homeValue = getOrDefault(request.getHomeValue(), BigDecimal.ZERO);
        Integer maturityPeriod = Optional.ofNullable(request.getMaturityPeriod()).orElse(0);

        validatePositiveNumber(income, "Income", errors);
        validatePositiveNumber(loanValue, "Loan value", errors);
        validatePositiveNumber(homeValue, "Home value", errors);

        if(maturityPeriod<=0) {
            errors.add("Maturity period must be minimum of 1 year");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private BigDecimal getOrDefault(BigDecimal value, BigDecimal defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }

    private void validatePositiveNumber(BigDecimal value, String fieldName, List<String> errors) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add(fieldName + " must be a positive number");
        }
    }
}