package com.premium.calc.service.engine;

import com.premium.calc.service.vo.Policy;
import com.premium.calc.service.vo.RiskType;

import java.math.BigDecimal;

/**
 * Concrete decorator for {@link RiskType#THEFT}
 */
public class PremiumTheftRiskLogic extends PremiumDecorator {
    private static final BigDecimal THRESHOLD = new BigDecimal("15") //
            , DEFAULT_COEFFICIENT_THEFT = new BigDecimal("0.11") //
            , ABOVE_THRESHOLD_COEFFICIENT_THEFT = new BigDecimal("0.05");

    public PremiumTheftRiskLogic(PremiumDecorated decorated) {
        super(decorated);
    }

    public BigDecimal calculate(Policy policy) {
        BigDecimal totalSumInsured = super.getTotalSumInsured(policy);
        final BigDecimal calculated;
        if (totalSumInsured.compareTo(THRESHOLD) > 0) {
            calculated = totalSumInsured.multiply(ABOVE_THRESHOLD_COEFFICIENT_THEFT);
        }else{
            calculated = totalSumInsured.multiply(DEFAULT_COEFFICIENT_THEFT);
        }
        final BigDecimal returned = decorated.calculate(policy).add(calculated);
        return returned;
    }

    @Override
    public RiskType getRiskType() {
        return RiskType.THEFT;
    }
}
