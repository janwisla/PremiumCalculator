package com.premium.calc.service.engine;

import com.premium.calc.service.vo.Policy;
import com.premium.calc.service.vo.RiskType;

import java.math.BigDecimal;

/**
 * Exposed public logic , implemented by all specific decorators
 */
public interface PremiumDecorated {
    /**
     * Calculates premium for given policy
     */
    BigDecimal calculate(Policy policy);
    /**
     * {@link RiskType supported by this decorator}
     */
    RiskType getRiskType();
}
