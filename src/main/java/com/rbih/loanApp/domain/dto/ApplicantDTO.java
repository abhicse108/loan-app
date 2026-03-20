package com.rbih.loanApp.domain.dto;

import com.rbih.loanApp.domain.enums.EmploymentType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApplicantDTO {

    @NotBlank
    private String name;

    @Min(21)
    @Max(60)
    private int age;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal monthlyIncome;

    @NotNull
    private EmploymentType employmentType;

    @Min(300)
    @Max(900)
    private int creditScore;
}