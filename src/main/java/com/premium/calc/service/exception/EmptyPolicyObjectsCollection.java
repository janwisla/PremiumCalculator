package com.premium.calc.service.exception;

/**
 * Thrown when {@link com.premium.calc.service.vo.Policy} contains empty Collection<PolicyObject> policyObjects
 */
public class EmptyPolicyObjectsCollection extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public EmptyPolicyObjectsCollection(String message) { super(message); }
}
