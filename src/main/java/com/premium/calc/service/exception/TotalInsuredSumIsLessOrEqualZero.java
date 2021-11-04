package com.premium.calc.service.exception;

/**
 * Thrown when result of premium calculation for given {@link com.premium.calc.service.vo.Policy} is less or equal 0
 */
public class TotalInsuredSumIsLessOrEqualZero extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public TotalInsuredSumIsLessOrEqualZero(String message) { super(message); }
}
