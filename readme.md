#Premium Calculator - design description

This module is spring component ready to calculate premium for policy based on given arguments.
It exposes PremiumCalculatorService as a spring bean,
Possible usage - mentioned bean can be injected into REST controller and it can serve HTTP requests.
Internally it uses Decorator pattern to implement various premium calculation depending on risk type.
Main interface contains below method 
```java
BigDecimal calculate(Policy policy);
```
It is important to note that by default module uses 
```java
java.math.RoundingMode.HALF_UP
```
rounding mode with 2 digit scale.
E.x. if during result of calculation is for example 11.755 it will be returned as 11.76
There are 2 situations when calculator will complain about given input data
 - when given Policy does not have nested policyObjects : EmptyPolicyObjectsCollection will be thrown 
 - when premium calculated from given Policy is less or equal 0 : TotalInsuredSumIsLessOrEqualZero will be thrown

Module contains several dedicated tests in PremiumCalculatorServiceTest class

##Requirements
Module was compiled with JAVA 11 and Apache Maven 3.8.3

##Future Enhancements
Enum com.premium.calc.service.vo.RiskType contains supported now risks
 - FIRE
 - THEFT

For purpose of adding completely new risk type it is enough to add new implementation of com.premium.calc.service.engine.PremiumDecorator



