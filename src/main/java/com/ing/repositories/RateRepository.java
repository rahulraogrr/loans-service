package com.ing.repositories;

import com.ing.entities.Rate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findByProductTypeAndRateType(String productType, String rateType);
    List<Rate> findByProductTypeAndRateTypeAndEffectiveDateOrderByEffectiveDateDesc(String productType, String rateType, LocalDate effectiveDate);
    Rate findByProductTypeAndRateTypeAndEffectiveDateAndMaturityPeriodOrderByEffectiveDateDesc(String productType, String rateType, LocalDate effectiveDate, Integer maturityPeriod);
    List<Rate> findByProductTypeAndRateTypeAndEffectiveDateLessThanEqualAndMaturityPeriodOrderByEffectiveDateDesc(
            String productType, String rateType, LocalDate effectiveDate, Integer maturityPeriod, Pageable pageable
    );
}
