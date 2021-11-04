package com.premium.calc.service;

import com.premium.calc.service.engine.EmptyLogic;
import com.premium.calc.service.engine.PremiumDecorated;
import com.premium.calc.service.engine.PremiumFireRiskLogic;
import com.premium.calc.service.engine.PremiumTheftRiskLogic;
import com.premium.calc.service.exception.EmptyPolicyObjectsCollection;
import com.premium.calc.service.exception.TotalInsuredSumIsLessOrEqualZero;
import com.premium.calc.service.vo.Policy;
import com.premium.calc.service.vo.PolicyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

public class PremiumCalculatorServiceImpl implements PremiumCalculatorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PremiumCalculatorServiceImpl.class);
    private final RoundingMode roundingMode;
    private final int scale;

    @Autowired
    public PremiumCalculatorServiceImpl(RoundingMode roundingMode, int scale) {
        this.roundingMode = roundingMode;
        this.scale = scale;
    }

    @Override
    public BigDecimal calculate(Policy policy) {
        final Collection<PolicyObject> policyObjects = policy.getPolicyObjects();
        if (CollectionUtils.isEmpty(policyObjects)) {
            final String message = "policyObjects collection is empty on policy policyNumber=" + policy.getPolicyNumber();
            LOGGER.error(message);
            throw new EmptyPolicyObjectsCollection(message);
        }
        final PremiumDecorated decorated = new PremiumFireRiskLogic(new PremiumTheftRiskLogic(new EmptyLogic()));
        final BigDecimal calculated = decorated.calculate(policy);
        if (calculated.compareTo(BigDecimal.ZERO) <= 0) {
            final String message = "Total sum insured is 0 on policy policyNumber=" + policy.getPolicyNumber();
            LOGGER.error(message);
            throw new TotalInsuredSumIsLessOrEqualZero(message);
        }
        final BigDecimal returned = calculated.setScale(scale, roundingMode);
        return returned;
    }
}
