package com.rbih.loanApp.service;

import com.rbih.loanApp.domain.enums.EmploymentType;
import com.rbih.loanApp.domain.enums.RiskBand;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InterestService {

    private static final BigDecimal BASE_RATE = BigDecimal.valueOf(12);

    public BigDecimal calculate(RiskBand riskBand,
                                EmploymentType employmentType, BigDecimal amount) {

        BigDecimal rate = BASE_RATE;

        // Risk premium
        switch (riskBand) {
            case MEDIUM -> rate = rate.add(BigDecimal.valueOf(1.5));
            case HIGH -> rate = rate.add(BigDecimal.valueOf(3));
        }

        // Employment premium
        if (employmentType == EmploymentType.SELF_EMPLOYED) {
            rate = rate.add(BigDecimal.ONE);
        } //Not required for SALARIED

        // Loan size premium
        if (amount.compareTo(BigDecimal.valueOf(1_000_000)) > 0) {
            rate = rate.add(BigDecimal.valueOf(0.5));
        }

        return rate;
    }
}
