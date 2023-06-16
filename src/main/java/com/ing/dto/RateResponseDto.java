package com.ing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class RateResponseDto {

    @Schema(
            title = "productType",
            name = "productType",
            example = "Mortgage",
            description = "Product type of the rate"
    )
    private final String productType;

    @Schema(
            title = "rateType",
            name = "rateType",
            example = "Fixed",
            description = "Rate type of the product"
    )
    private final String rateType;

    @Schema(
            title = "effectiveDate",
            name = "effectiveDate",
            example = "23-05-2023",
            description = "Effective date"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate effectiveDate;

    @Schema(
            title = "maturityPeriod",
            name = "maturityPeriod",
            example = "5",
            description = "Maturity Period"
    )
    private final Integer maturityPeriod;

    @Schema(
            title = "interestRate",
            name = "interestRate",
            example = "4.0",
            description = "Interest Rate"
    )
    private final BigDecimal interestRate;
}