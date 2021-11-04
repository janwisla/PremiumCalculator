package com.premium.calc.service.engine;

import com.premium.calc.service.vo.Policy;
import com.premium.calc.service.vo.RiskType;

import java.math.BigDecimal;

/**
 * Concrete decorator for {@link RiskType#FIRE}
 */
public class PremiumFireRiskLogic extends PremiumDecorator {
    private static final BigDecimal THRESHOLD = new BigDecimal("100") //
            , DEFAULT_COEFFICIENT_FIRE = new BigDecimal("0.014") //
            , ABOVE_THRESHOLD_COEFFICIENT_FIRE = new BigDecimal("0.024");

    public PremiumFireRiskLogic(PremiumDecorated decorated) {
        super(decorated);
    }

    public BigDecimal calculate(Policy policy) {
        BigDecimal totalSumInsured = super.getTotalSumInsured(policy);
        final BigDecimal calculated;
        if (totalSumInsured.compareTo(THRESHOLD) > 0) {
            calculated = totalSumInsured.multiply(ABOVE_THRESHOLD_COEFFICIENT_FIRE);
        }else{
            calculated = totalSumInsured.multiply(DEFAULT_COEFFICIENT_FIRE);
        }
        final BigDecimal returned = decorated.calculate(policy).add(calculated);
        return returned;
    }

    @Override
    public RiskType getRiskType() {
        return RiskType.FIRE;
    }
}
