package com.ing.services.impl;

import com.ing.dto.MortgageCheckRequest;
import com.ing.dto.MortgageCheckResponse;
import com.ing.services.helpers.MortgageHelper;
import com.ing.services.MortgageService;
import com.ing.services.validation.MortgageValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MortgageServiceImpl implements MortgageService {

    private final MortgageValidation mortgageValidation;
    private final MortgageHelper mortgageHelper;

    @Override
    public MortgageCheckResponse performMortgageFeasibilityCheck(MortgageCheckRequest request) {
        mortgageValidation.validate(request);
        return mortgageHelper.checkFeasibility(request);
    }

}
