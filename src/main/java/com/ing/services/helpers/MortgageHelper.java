package com.ing.services.helpers;

import com.ing.dto.MortgageCheckRequest;
import com.ing.dto.MortgageCheckResponse;
import com.ing.entities.Rate;
import com.ing.exceptions.custom.ResourceNotFoundException;
import com.ing.repositories.RateRepository;
import com.ing.rules.impl.MortgageFeasibilityRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.ing.constants.MortgageFeasibilityConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MortgageHelper {
    private final RateRepository rateRepository;
    private final MortgageFeasibilityRule mortgageFeasibilityRule;

    public MortgageCheckResponse checkFeasibility(MortgageCheckRequest request) {
        log.info("Check Feasibility");
        MortgageCheckResponse response = new MortgageCheckResponse();
        boolean isLoanValueExceed = mortgageFeasibilityRule.evaluateCondition(request, LOAN_VALUE_EXCEED_CHECK);
        boolean isLoanGreaterHome = mortgageFeasibilityRule.evaluateCondition(request, LOAN_VALUE_GREATER_THAN_HOME_VALUE);

        if (isLoanValueExceed || isLoanGreaterHome) {
            StringBuilder reason = new StringBuilder();
            response.setIsFeasible(false);

            if (isLoanValueExceed) {
                reason.append("Loan value cannot exceed ").append(MortgageFeasibilityRule.MAX_INCOME_MULTIPLIER).append(" times the income");
            }

            if (isLoanGreaterHome) {
                if (reason.length() > 0) {
                    reason.append(" and ");
                }
                reason.append("Loan value cannot be greater than Home value");
            }
            log.info("Not Feasible "+reason.toString());
            response.setReason(reason.toString());
        } else {
            response.setIsFeasible(true);
            response.setEmi(calculateEmi(request));
            log.info("Feasible");
        }

        return response;
    }

    /**
     * Calculates the Equated Monthly Installment (EMI) for a mortgage loan based on the provided request parameters.
     * The EMI is calculated using the loan amount, maturity period, and the applicable interest rate.
     *
     * Formula:
     * EMI = (loanAmount * monthlyInterestRate) / (1 - (1 + monthlyInterestRate)^(-numberOfPayments))
     *
     * Example calculation:
     * For a loan amount of €100,000, a maturity period of 20 years (240 months), and an annual interest rate of 6%,
     * the monthly EMI can be calculated as follows:
     *  - loanAmount = €100,000
     *  - maturityPeriod = 20 years (240 months)
     *  - annualInterestRate = 6%
     *
     *  - monthlyInterestRate = (annualInterestRate / 100) / 12
     *    monthlyInterestRate = (6 / 100) / 12 = 0.005
     *
     *  - numberOfPayments = maturityPeriod * 12
     *    numberOfPayments = 240
     *
     *  - EMI = (loanAmount * monthlyInterestRate) / (1 - (1 + monthlyInterestRate)^(-numberOfPayments))
     *    EMI = (100,000 * 0.005) / (1 - (1 + 0.005)^(-240))
     *
     * @param request The MortgageCheckRequest object containing the loan details.
     *                It should include the loan amount and maturity period.
     * @return The calculated EMI (Equated Monthly Installment) as a BigDecimal rounded to 2 decimal places.
     */
    private BigDecimal calculateEmi(MortgageCheckRequest request) {
        double annualInterestRate = BigDecimal.ZERO.doubleValue();

        List<Rate> rates = rateRepository.findByProductTypeAndRateTypeAndEffectiveDateLessThanEqualAndMaturityPeriodOrderByEffectiveDateDesc(
                "Mortgage",
                "Fixed",
                LocalDate.now(),
                request.getMaturityPeriod(),
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "effectiveDate"))
        );

        Rate rate = rates.isEmpty() ? null : rates.get(0);

        if (rate != null && Objects.equals(rate.getMaturityPeriod(), request.getMaturityPeriod())) {
            annualInterestRate = rate.getInterestRate().doubleValue();
        }else {
            throw new ResourceNotFoundException("Rate not found for maturity period of "+request.getMaturityPeriod());
        }

        BigDecimal loanAmount = request.getLoanValue();

        int numberOfPayments = request.getMaturityPeriod() * 12;

        double monthlyInterestRate = annualInterestRate / 100.0 / 12.0;
        double emi = (loanAmount.doubleValue() * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
        BigDecimal emiBigDecimal = new BigDecimal(emi);
        return emiBigDecimal.setScale(2, RoundingMode.HALF_UP);
    }
}