package com.ing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MortgageCheckRequest {

    @Schema(
            title = "income",
            name = "income",
            example = "5000.00",
            description = "User income"
    )
    private BigDecimal income;

    @Schema(
            title = "maturityPeriod",
            name = "maturityPeriod",
            example = "1",
            description = "Maturity Period of the loan",
            nullable = false
    )
    private Integer maturityPeriod;

    @Schema(
            title = "loanValue",
            name = "loanValue",
            example = "10000.00",
            description = "Principal amount of the loan"
    )
    private BigDecimal loanValue;

    @Schema(
            title = "homeValue",
            name = "homeValue",
            example = "15000.00",
            description = "Property Value"
    )
    private BigDecimal homeValue;
}