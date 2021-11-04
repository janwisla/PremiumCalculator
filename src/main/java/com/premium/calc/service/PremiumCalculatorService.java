package com.premium.calc.service;

import com.premium.calc.service.vo.Policy;

import java.math.BigDecimal;

/**
 * Main exposed public interface
 */
public interface PremiumCalculatorService {
    /**
     * Calculates premium for given policy
     */
    BigDecimal calculate(Policy policy);
}
