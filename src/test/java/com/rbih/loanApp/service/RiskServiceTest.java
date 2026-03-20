package com.rbih.loanApp.service;

import com.rbih.loanApp.domain.dto.ApplicantDTO;
import com.rbih.loanApp.domain.dto.CreateLoanApplicationRequest;
import com.rbih.loanApp.domain.dto.LoanApplicationResponse;
import com.rbih.loanApp.domain.dto.LoanDTO;
import com.rbih.loanApp.domain.enums.EmploymentType;
import com.rbih.loanApp.domain.enums.LoanPurpose;
import com.rbih.loanApp.domain.enums.RiskBand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;



class RiskServiceTest {

    private RiskService riskService;

    @BeforeEach
    void setUp() {
        riskService = new RiskService();
    }

    @Test
    void shouldReturnLowRiskForScore750AndAbove() {
        assertEquals(RiskBand.LOW, riskService.determineRisk(750));
        assertEquals(RiskBand.LOW, riskService.determineRisk(800));
    }

    @Test
    void shouldReturnMediumRiskForScoreBetween650And749() {
        assertEquals(RiskBand.MEDIUM, riskService.determineRisk(650));
        assertEquals(RiskBand.MEDIUM, riskService.determineRisk(700));
        assertEquals(RiskBand.MEDIUM, riskService.determineRisk(749));
    }

    @Test
    void shouldReturnHighRiskForScoreBelow650() {
        assertEquals(RiskBand.HIGH, riskService.determineRisk(600));
        assertEquals(RiskBand.HIGH, riskService.determineRisk(649));
    }
}