package com.ing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class MortgageCheckResponse {
    private Boolean isFeasible;
    private String reason;
    private BigDecimal emi;
}