package com.premium.calc.service.engine;

import com.premium.calc.service.vo.Policy;
import com.premium.calc.service.vo.PolicyObject;
import com.premium.calc.service.vo.PolicySubObject;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract decorator implementation
 */
public abstract class PremiumDecorator implements PremiumDecorated{
    protected PremiumDecorated decorated;
    public PremiumDecorator(PremiumDecorated decorated) {
        this.decorated = decorated;
    }
    /**
     * Calculates total insured sum of all sub objects where riskj type is supported by specific implementation.
     * @param policy given Policy
     * @return total insured sum
     */
    protected BigDecimal getTotalSumInsured(Policy policy){
        BigDecimal returned = BigDecimal.ZERO;
        for (PolicyObject policyObject : policy.getPolicyObjects()) {
            final Collection<PolicySubObject> subObjects = policyObject.getSubObjects();
            final List<PolicySubObject> collected = subObjects.stream() //
                    .filter(subObject -> this.getRiskType() == subObject.getRiskType()).collect(Collectors.toList());
            for (PolicySubObject subObject : collected) {
                returned = returned.add(subObject.getSumInsured());
            }
        }
        return returned;
    }
}
