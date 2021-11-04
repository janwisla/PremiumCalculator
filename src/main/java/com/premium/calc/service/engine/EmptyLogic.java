package com.premium.calc.service.engine;

import com.premium.calc.service.vo.Policy;
import com.premium.calc.service.vo.RiskType;

import java.math.BigDecimal;

/**
 * Basic risk logic
 */
public class EmptyLogic implements PremiumDecorated{
    @Override
    public BigDecimal calculate(Policy policy) {
        return BigDecimal.ZERO;
    }

    @Override
    public RiskType getRiskType() {
        return null;
    }
}
