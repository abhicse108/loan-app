package com.rbih.loanApp.service;

import org.junit.jupiter.api.Test;

import com.rbih.loanApp.domain.dto.ApplicantDTO;
import com.rbih.loanApp.domain.dto.LoanDTO;
import com.rbih.loanApp.domain.enums.EmploymentType;
import com.rbih.loanApp.domain.enums.LoanPurpose;
import com.rbih.loanApp.domain.enums.RejectionReason;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EligibilityServiceTest {

    private EligibilityService eligibilityService;

    @BeforeEach
    void setUp() {
        eligibilityService = new EligibilityService();
    }

    @Test
    void shouldRejectIfCreditScoreBelow600() {
        ApplicantDTO applicant = new ApplicantDTO();
        applicant.setAge(30);
        applicant.setMonthlyIncome(BigDecimal.valueOf(50000));
        applicant.setEmploymentType(EmploymentType.SALARIED);
        applicant.setCreditScore(550);

        LoanDTO loan = new LoanDTO();
        loan.setAmount(BigDecimal.valueOf(200000));
        loan.setTenureMonths(24);
        loan.setPurpose(LoanPurpose.PERSONAL);

        BigDecimal emi = BigDecimal.valueOf(5000);

        List<RejectionReason> reasons = eligibilityService.check(applicant, loan, emi);

        assertTrue(reasons.contains(RejectionReason.LOW_CREDIT_SCORE));
    }

    @Test
    void shouldRejectIfAgePlusTenureExceedsLimit() {
        ApplicantDTO applicant = new ApplicantDTO();
        applicant.setAge(60);
        applicant.setMonthlyIncome(BigDecimal.valueOf(50000));
        applicant.setEmploymentType(EmploymentType.SALARIED);
        applicant.setCreditScore(700);

        LoanDTO loan = new LoanDTO();
        loan.setAmount(BigDecimal.valueOf(200000));
        loan.setTenureMonths(120);
        loan.setPurpose(LoanPurpose.PERSONAL);

        BigDecimal emi = BigDecimal.valueOf(5000);

        List<RejectionReason> reasons = eligibilityService.check(applicant, loan, emi);

        assertTrue(reasons.contains(RejectionReason.AGE_TENURE_LIMIT_EXCEEDED));
    }

    @Test
    void shouldRejectIfEmiExceeds60PercentIncome() {
        ApplicantDTO applicant = new ApplicantDTO();
        applicant.setAge(30);
        applicant.setMonthlyIncome(BigDecimal.valueOf(20000));
        applicant.setEmploymentType(EmploymentType.SALARIED);
        applicant.setCreditScore(700);

        LoanDTO loan = new LoanDTO();
        loan.setAmount(BigDecimal.valueOf(200000));
        loan.setTenureMonths(24);
        loan.setPurpose(LoanPurpose.PERSONAL);

        BigDecimal emi = BigDecimal.valueOf(15000);

        List<RejectionReason> reasons = eligibilityService.check(applicant, loan, emi);

        assertTrue(reasons.contains(RejectionReason.EMI_EXCEEDS_60_PERCENT));
    }

    @Test
    void shouldPassAllEligibilityChecks() {
        ApplicantDTO applicant = new ApplicantDTO();
        applicant.setAge(30);
        applicant.setMonthlyIncome(BigDecimal.valueOf(50000));
        applicant.setEmploymentType(EmploymentType.SALARIED);
        applicant.setCreditScore(750);

        LoanDTO loan = new LoanDTO();
        loan.setAmount(BigDecimal.valueOf(200000));
        loan.setTenureMonths(24);
        loan.setPurpose(LoanPurpose.PERSONAL);

        BigDecimal emi = BigDecimal.valueOf(10000);

        List<RejectionReason> reasons = eligibilityService.check(applicant, loan, emi);

        assertTrue(reasons.isEmpty());
    }

    @Test
    void shouldRejectIfEmiExceeds50PercentForOffer() {
        BigDecimal income = BigDecimal.valueOf(20000);
        BigDecimal emi = BigDecimal.valueOf(12000);

        boolean result = eligibilityService.isEligibleForOffer(emi, income);

        assertFalse(result);
    }

    @Test
    void shouldAllowIfEmiWithin50PercentForOffer() {
        BigDecimal income = BigDecimal.valueOf(20000);
        BigDecimal emi = BigDecimal.valueOf(9000);

        boolean result = eligibilityService.isEligibleForOffer(emi, income);

        assertTrue(result);
    }
}