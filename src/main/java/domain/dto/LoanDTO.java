package domain.dto;

import domain.enums.LoanPurpose;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanDTO {

    @NotNull
    @DecimalMin("10000")
    @DecimalMax("5000000")
    private BigDecimal amount;

    @Min(6)
    @Max(360)
    private int tenureMonths;

    @NotNull
    private LoanPurpose purpose;
}
