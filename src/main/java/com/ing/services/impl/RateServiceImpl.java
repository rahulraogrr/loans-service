package com.ing.services.impl;

import com.ing.entities.Rate;
import com.ing.repositories.RateRepository;
import com.ing.services.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;

    @Override
    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }

}
