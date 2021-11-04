package com.premium.calc;

import com.premium.calc.service.PremiumCalculatorService;
import com.premium.calc.service.exception.EmptyPolicyObjectsCollection;
import com.premium.calc.service.exception.TotalInsuredSumIsLessOrEqualZero;
import com.premium.calc.service.vo.Policy;
import com.premium.calc.service.vo.PolicyObject;
import com.premium.calc.service.vo.PolicyStatus;
import com.premium.calc.service.vo.PolicySubObject;
import com.premium.calc.service.vo.RiskType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DelegatingSmartContextLoader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring-test.xml", loader = DelegatingSmartContextLoader.class)
public class PremiumCalculatorServiceTest {

    @Autowired
    private PremiumCalculatorService service;

    @Test
    public void shouldCalculatePremiumFor2SubObjects() {
        final Collection<PolicySubObject> subObjects = new ArrayList<>();
        subObjects.add(new PolicySubObject("iMac", new BigDecimal(8), RiskType.THEFT));
        subObjects.add(new PolicySubObject("iMac", new BigDecimal(100), RiskType.FIRE));
        final PolicyObject policyObject = new PolicyObject("House", subObjects);
        final Policy policy = new Policy("LV20-02-100000-5", PolicyStatus.APPROVED, List.of(policyObject));
        BigDecimal calculated = service.calculate(policy);
        final float expected = 2.28F;
        assert expected == calculated.floatValue() : "Incorrect value calculated, expected=" + expected + " got=" + calculated;
    }

    @Test
    public void shouldCalculatePremiumComplexExample() {
        final Collection<PolicySubObject> wareHouseSubObjects = new ArrayList<>();
        wareHouseSubObjects.add(new PolicySubObject("Dodge Challenger", new BigDecimal(50), RiskType.THEFT));
        wareHouseSubObjects.add(new PolicySubObject("Dodge Challenger", new BigDecimal(250), RiskType.FIRE));
        wareHouseSubObjects.add(new PolicySubObject("Ducati", new BigDecimal(250), RiskType.FIRE));
        wareHouseSubObjects.add(new PolicySubObject("Ducati", new BigDecimal("52.51"), RiskType.THEFT));
        final PolicyObject warehouse = new PolicyObject("Warehouse", wareHouseSubObjects);
        final Policy policy = new Policy("LV10-02-100807-5", PolicyStatus.REGISTERED, List.of(warehouse));
        BigDecimal calculated = service.calculate(policy);
        final float expected = 17.13F;
        assert expected == calculated.floatValue() : "Incorrect value calculated, expected=" + expected + " got=" + calculated;
    }

    @Test
    public void shouldRoundCalculatedPremiumHalfUp() {
        final Collection<PolicySubObject> wareHouseSubObjects = new ArrayList<>();
        wareHouseSubObjects.add(new PolicySubObject("Expensive desk", new BigDecimal("117.4"), RiskType.FIRE));
        final PolicyObject office = new PolicyObject("Warehouse", wareHouseSubObjects);
        final Policy policy = new Policy("LV10-02-100840-5", PolicyStatus.APPROVED, List.of(office));
        BigDecimal calculated = service.calculate(policy);
        final float expected = 2.82F;
        assert expected == calculated.floatValue() : "Incorrect value calculated, expected=" + expected + " got=" + calculated;
    }

    @Test
    public void shouldReturnBigDecimalWithScale2() {
        final Collection<PolicySubObject> livingRoomSubObjects = new ArrayList<>();
        livingRoomSubObjects.add(new PolicySubObject("Plasma TV", new BigDecimal("88.17"), RiskType.FIRE));
        final PolicyObject livingRoom = new PolicyObject("Living Room", livingRoomSubObjects);
        final Policy policy = new Policy("LV10-02-900840-5", PolicyStatus.APPROVED, List.of(livingRoom));
        BigDecimal calculated = service.calculate(policy);
        final int scale = calculated.scale();
        final int expected = 2;
        assert expected == scale : "Incorrect sscale in returned  calculated BigDecimal object, expected=" + expected + " got=" + scale;
    }

    @Test(expected = EmptyPolicyObjectsCollection.class)
    public void shouldPreventPolicyWithEmptyPolicyObject(){
        service.calculate(new Policy("LV15-02-100800-5", PolicyStatus.REGISTERED, Collections.emptyList()));
    }

    @Test(expected = TotalInsuredSumIsLessOrEqualZero.class)
    public void shouldPreventPolicyWithTotalInsuredSumIsLessOrEqualZero(){
        final Collection<PolicySubObject> subObjects = new ArrayList<>();
        subObjects.add(new PolicySubObject("Bicycle", BigDecimal.ZERO, RiskType.THEFT));
        subObjects.add(new PolicySubObject("Nokia", BigDecimal.ZERO, RiskType.FIRE));
        subObjects.add(new PolicySubObject("Stamps collection", BigDecimal.ZERO, RiskType.FIRE));
        final PolicyObject policyObject = new PolicyObject("Warehouse", subObjects);
        service.calculate(new Policy("LV10-02-100800-5", PolicyStatus.REGISTERED, List.of(policyObject)));
    }
}
