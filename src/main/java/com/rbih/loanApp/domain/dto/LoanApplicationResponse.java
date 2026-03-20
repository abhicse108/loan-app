package com.rbih.loanApp.domain.dto;

import com.rbih.loanApp.domain.enums.ApplicationStatus;
import com.rbih.loanApp.domain.enums.RejectionReason;
import com.rbih.loanApp.domain.enums.RiskBand;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class LoanApplicationResponse {

    private UUID applicationId;
    private ApplicationStatus status;
    private RiskBand riskBand;
    private OfferDTO offer;
    private List<RejectionReason> rejectionReasons;
}
