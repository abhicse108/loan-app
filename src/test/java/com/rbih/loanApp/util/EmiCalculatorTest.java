package com.rbih.loanApp.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EmiCalculatorTest {

    private EmiCalculator emiCalculator;

    @BeforeEach
    void setUp() {
        emiCalculator = new EmiCalculator();
    }

    @Test
    void shouldCalculateEmiCorrectly() {
        BigDecimal principal = BigDecimal.valueOf(500000);
        BigDecimal rate = BigDecimal.valueOf(12);
        int tenure = 36;

        BigDecimal emi = emiCalculator.calculate(principal, rate, tenure);

        assertNotNull(emi);
        assertTrue(emi.compareTo(BigDecimal.ZERO) > 0);

    }

    @Test
    void shouldHandleHighInterestRate() {
        BigDecimal principal = BigDecimal.valueOf(100000);
        BigDecimal rate = BigDecimal.valueOf(20);
        int tenure = 12;

        BigDecimal emi = emiCalculator.calculate(principal, rate, tenure);

        assertNotNull(emi);
        assertTrue(emi.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void shouldHandleLongTenure() {
        BigDecimal principal = BigDecimal.valueOf(1000000);
        BigDecimal rate = BigDecimal.valueOf(10);
        int tenure = 240;

        BigDecimal emi = emiCalculator.calculate(principal, rate, tenure);

        assertNotNull(emi);
        assertTrue(emi.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void shouldReturnHigherEmiForHigherInterestRate() {
        BigDecimal principal = BigDecimal.valueOf(500000);
        int tenure = 36;

        BigDecimal emiLowRate = emiCalculator.calculate(principal, BigDecimal.valueOf(10), tenure);
        BigDecimal emiHighRate = emiCalculator.calculate(principal, BigDecimal.valueOf(15), tenure);

        assertTrue(emiHighRate.compareTo(emiLowRate) > 0);
    }

    @Test
    void shouldReturnLowerEmiForLongerTenure() {
        BigDecimal principal = BigDecimal.valueOf(500000);
        BigDecimal rate = BigDecimal.valueOf(12);

        BigDecimal emiShort = emiCalculator.calculate(principal, rate, 12);
        BigDecimal emiLong = emiCalculator.calculate(principal, rate, 60);

        assertTrue(emiLong.compareTo(emiShort) < 0);
    }
}