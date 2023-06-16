package com.ing.entities;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Rate Entity
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_rates")
public class Rate implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "rate_type")
    private String rateType;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "maturity_period")
    private Integer maturityPeriod;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "create_ts")
    @CreationTimestamp
    private LocalDateTime createTs;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime updateTs;
}