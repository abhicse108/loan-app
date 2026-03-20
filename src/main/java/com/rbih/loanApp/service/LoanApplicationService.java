package com.rbih.loanApp.service;

import com.rbih.loanApp.domain.dto.CreateLoanApplicationRequest;
import com.rbih.loanApp.domain.dto.LoanApplicationResponse;
import com.rbih.loanApp.domain.enums.ApplicationStatus;
import com.rbih.loanApp.domain.enums.RejectionReason;
import com.rbih.loanApp.domain.enums.RiskBand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.rbih.loanApp.util.EmiCalculator;
import com.rbih.loanApp.domain.dto.OfferDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final RiskService riskService;
    private final InterestService interestService;
    private final EmiCalculator emiCalculator;
    private final EligibilityService eligibilityService;

//    public LoanApplicationService(RiskService riskService, InterestService interestService, EmiCalculator emiCalculator, EligibilityService eligibilityService) {
//        this.riskService = riskService;
//        this.interestService = interestService;
//        this.emiCalculator = emiCalculator;
//        this.eligibilityService = eligibilityService;
//    }

    public LoanApplicationResponse processApplication(CreateLoanApplicationRequest request) {

        UUID id = UUID.randomUUID();

        RiskBand riskBand = riskService.determineRisk(request.getApplicant().getCreditScore());

        BigDecimal interestRate = interestService.calculate(
                riskBand,
                request.getApplicant().getEmploymentType(),
                request.getLoan().getAmount()
        );

        BigDecimal emi = emiCalculator.calculate(
                request.getLoan().getAmount(),
                interestRate,
                request.getLoan().getTenureMonths()
        );

        List<RejectionReason> reasons = eligibilityService.check(
                request.getApplicant(),
                request.getLoan(),
                emi
        );

        if (!reasons.isEmpty()) {
            return LoanApplicationResponse.builder()
                    .applicationId(id)
                    .status(ApplicationStatus.REJECTED)
                    .rejectionReasons(reasons)
                    .build();
        }

        if (!eligibilityService.isEligibleForOffer(emi, request.getApplicant().getMonthlyIncome())) {
            return LoanApplicationResponse.builder()
                    .applicationId(id)
                    .status(ApplicationStatus.REJECTED)
                    .rejectionReasons(List.of(RejectionReason.EMI_EXCEEDS_50_PERCENT))
                    .build();
        }

        BigDecimal totalPayable = emi.multiply(BigDecimal.valueOf(request.getLoan().getTenureMonths()));

        return LoanApplicationResponse.builder()
                .applicationId(id)
                .status(ApplicationStatus.APPROVED)
                .riskBand(riskBand)
                .offer(OfferDTO.builder()
                        .interestRate(interestRate)
                        .tenureMonths(request.getLoan().getTenureMonths())
                        .emi(emi)
                        .totalPayable(totalPayable)
                        .build())
                .build();
    }
}
