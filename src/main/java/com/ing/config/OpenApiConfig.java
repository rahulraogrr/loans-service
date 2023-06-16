package com.ing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Loans API")
                                .version("1.0")
                                .description(
                                        "API for managing loans and mortgage calculations.\n\n" +
                                                "Endpoints:\n" +
                                                "1. Get Interest Rates\n\n" +
                                                "Retrieves all interest rates for different product types and rate types.\n" +
                                                "Endpoint: GET /api/interest-rates\n\n" +
                                                "2. Check Mortgage Feasibility\n\n" +
                                                "Checks the feasibility of a mortgage loan based on the provided loan details. " +
                                                "It validates the loan value and the loan-to-income ratio. If the loan value " +
                                                "exceeds the maximum allowed based on the borrower's income or if the loan " +
                                                "value is greater than the home value, the response will indicate that the " +
                                                "mortgage is not feasible. Otherwise, it calculates the Equated Monthly " +
                                                "Installment (EMI) for the mortgage loan and returns the feasibility check " +
                                                "response.\n" +
                                                "\n" +
                                                "Endpoint: POST /api/mortgage-check\n" +
                                                "\n" +
                                                "Calculates the Equated Monthly Installment (EMI) for a mortgage loan based on the provided request parameters.\n" +
                                                "     The EMI is calculated using the loan amount, maturity period, and the applicable interest rate.\n" +
                                                "     \n" +
                                                "      Formula:\n" +
                                                "      EMI = (loanAmount * monthlyInterestRate) / (1 - (1 + monthlyInterestRate)^(-numberOfPayments))\n" +
                                                "     \n" +
                                                "      Example calculation:\n" +
                                                "      For a loan amount of €100,000, a maturity period of 20 years (240 months), and an annual interest rate of 6%,\n" +
                                                "      the monthly EMI can be calculated as follows:\n" +
                                                "       - loanAmount = €100,000\n" +
                                                "       - maturityPeriod = 20 years (240 months)\n" +
                                                "       - annualInterestRate = 6%\n" +
                                                "     \n" +
                                                "       - monthlyInterestRate = (annualInterestRate / 100) / 12\n" +
                                                "         monthlyInterestRate = (6 / 100) / 12 = 0.005\n" +
                                                "     \n" +
                                                "       - numberOfPayments = maturityPeriod * 12\n" +
                                                "         numberOfPayments = 240\n" +
                                                "     \n" +
                                                "       - EMI = (loanAmount * monthlyInterestRate) / (1 - (1 + monthlyInterestRate)^(-numberOfPayments))\n" +
                                                "         EMI = (100,000 * 0.005) / (1 - (1 + 0.005)^(-240)) \n" +
                                                "         EMI = €1132.88"
                                )
                                .termsOfService("https://www.linkedin.com/in/rahulraogrr/")
                                .contact(
                                        new Contact()
                                                .name("Rahul Rao Gonda")
                                                .url("https://www.linkedin.com/in/rahulraogrr/")
                                                .email("rahulrao.grr@gmail.com")
                                )
                );
    }
}

