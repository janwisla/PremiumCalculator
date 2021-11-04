package com.premium.calc.service.vo;

import com.premium.calc.service.exception.EmptyPolicyObjectsCollection;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Represents insurance policy.
 */
public class Policy {
    /** example : LV10-02-100800-5 */
    private final String policyNumber;
    private final PolicyStatus policyStatus;
    /** Objects covered by this policy */
    private final Collection<PolicyObject> policyObjects;

    public Policy(String policyNumber, PolicyStatus policyStatus, Collection<PolicyObject> policyObjects) {
        this.policyNumber = policyNumber;
        this.policyStatus = policyStatus;
        this.policyObjects = policyObjects;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    public Collection<PolicyObject> getPolicyObjects() {
        return policyObjects;
    }
}
