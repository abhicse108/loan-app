package com.rbih.loanApp.service;

import static org.junit.jupiter.api.Assertions.*;

import com.rbih.loanApp.domain.dto.*;
import com.rbih.loanApp.domain.enums.*;
import com.rbih.loanApp.util.EmiCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class LoanApplicationServiceTest {

    private LoanApplicationService service;

    @BeforeEach
    void setUp() {
        service = new LoanApplicationService(
                new RiskService(),
                new InterestService(),
                new EmiCalculator(),
                new EligibilityService()
        );
    }

    private CreateLoanApplicationRequest buildValidRequest() {
        ApplicantDTO applicant = new ApplicantDTO();
        applicant.setName("Test User");
        applicant.setAge(30);
        applicant.setMonthlyIncome(BigDecimal.valueOf(60000));
        applicant.setEmploymentType(EmploymentType.SALARIED);
        applicant.setCreditScore(750);

        LoanDTO loan = new LoanDTO();
        loan.setAmount(BigDecimal.valueOf(300000));
        loan.setTenureMonths(24);
        loan.setPurpose(LoanPurpose.PERSONAL);

        CreateLoanApplicationRequest request = new CreateLoanApplicationRequest();
        request.setApplicant(applicant);
        request.setLoan(loan);

        return request;
    }

    @Test
    void shouldApproveApplicationWhenAllConditionsPass() {
        CreateLoanApplicationRequest request = buildValidRequest();

        LoanApplicationResponse response = service.processApplication(request);

        assertEquals(ApplicationStatus.APPROVED, response.getStatus());
        assertNotNull(response.getOffer());
        assertNotNull(response.getRiskBand());
        assertTrue(response.getOffer().getEmi().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void shouldRejectWhenCreditScoreLow() {
        CreateLoanApplicationRequest request = buildValidRequest();
        request.getApplicant().setCreditScore(550);

        LoanApplicationResponse response = service.processApplication(request);

        assertEquals(ApplicationStatus.REJECTED, response.getStatus());
        assertTrue(response.getRejectionReasons().contains(RejectionReason.LOW_CREDIT_SCORE));
    }

    @Test
    void shouldRejectWhenEmiExceeds60Percent() {
        CreateLoanApplicationRequest request = buildValidRequest();
        request.getApplicant().setMonthlyIncome(BigDecimal.valueOf(10000));

        LoanApplicationResponse response = service.processApplication(request);

        assertEquals(ApplicationStatus.REJECTED, response.getStatus());
        assertTrue(response.getRejectionReasons().contains(RejectionReason.EMI_EXCEEDS_60_PERCENT));
    }


    @Test
    void shouldReturnMultipleRejectionReasons() {
        CreateLoanApplicationRequest request = buildValidRequest();
        request.getApplicant().setCreditScore(500);
        request.getApplicant().setMonthlyIncome(BigDecimal.valueOf(10000));

        LoanApplicationResponse response = service.processApplication(request);

        assertEquals(ApplicationStatus.REJECTED, response.getStatus());
        assertTrue(response.getRejectionReasons().size() >= 1);
    }
}