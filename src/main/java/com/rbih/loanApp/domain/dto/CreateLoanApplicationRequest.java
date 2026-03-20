package com.rbih.loanApp.domain.dto;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class CreateLoanApplicationRequest {

    @Valid
    private ApplicantDTO applicant;

    @Valid
    private LoanDTO loan;
}