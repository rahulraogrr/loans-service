package com.ing.services;

import com.ing.dto.MortgageCheckRequest;
import com.ing.dto.MortgageCheckResponse;

public interface MortgageService {
    MortgageCheckResponse performMortgageFeasibilityCheck(MortgageCheckRequest request);
}
