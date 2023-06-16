package com.ing.mappers;

import com.ing.dto.RateResponseDto;
import com.ing.entities.Rate;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RateResponseDtoMapper implements Function<Rate, RateResponseDto> {

    @Override
    public RateResponseDto apply(Rate rate) {
        return RateResponseDto.builder()
                .productType(rate.getProductType())
                .rateType(rate.getRateType())
                .interestRate(rate.getInterestRate())
                .maturityPeriod(rate.getMaturityPeriod())
                .effectiveDate(rate.getEffectiveDate())
                .build();
    }
}