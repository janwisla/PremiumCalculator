package com.premium.calc.service.vo;

import java.math.BigDecimal;

/**
 * Represents concrete subobject with given insurance sum.
 */
public class PolicySubObject {
    private final String name;
    private final BigDecimal sumInsured;
    private final RiskType riskType;

    public PolicySubObject(String name, BigDecimal sumInsured, RiskType riskType) {
        this.name = name;
        this.sumInsured = sumInsured;
        this.riskType = riskType;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSumInsured() {
        return sumInsured;
    }

    public RiskType getRiskType() {
        return riskType;
    }
}
