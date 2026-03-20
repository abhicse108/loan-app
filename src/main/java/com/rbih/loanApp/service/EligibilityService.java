package com.rbih.loanApp.service;

import com.rbih.loanApp.domain.dto.ApplicantDTO;
import com.rbih.loanApp.domain.dto.LoanDTO;
import com.rbih.loanApp.domain.enums.RejectionReason;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EligibilityService {

    public List<RejectionReason> check(
            ApplicantDTO applicant,
            LoanDTO loan,
            BigDecimal emi
    ) {
        List<RejectionReason> reasons = new ArrayList<>();

        if (applicant.getCreditScore() < 600) {
            reasons.add(RejectionReason.LOW_CREDIT_SCORE);
        }

        int ageAtEnd = applicant.getAge() + (loan.getTenureMonths() / 12);
        if (ageAtEnd > 65) {
            reasons.add(RejectionReason.AGE_TENURE_LIMIT_EXCEEDED);
        }

        BigDecimal sixtyPercentIncome = applicant.getMonthlyIncome().multiply(BigDecimal.valueOf(0.6));
        if (emi.compareTo(sixtyPercentIncome) > 0) {
            reasons.add(RejectionReason.EMI_EXCEEDS_60_PERCENT);
        }

        return reasons;
    }

    public boolean isEligibleForOffer(BigDecimal emi, BigDecimal income) {
        return emi.compareTo(income.multiply(BigDecimal.valueOf(0.5))) <= 0;
    }
}
