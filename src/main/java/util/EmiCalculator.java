package util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class EmiCalculator {

    public BigDecimal calculate(BigDecimal principal, BigDecimal annualRate, int months) {

        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        BigDecimal compoundTerm = (monthlyRate.add(BigDecimal.ONE)).pow(months);

        BigDecimal numerator = principal.multiply(monthlyRate).multiply(compoundTerm);
        BigDecimal denominator = compoundTerm.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}