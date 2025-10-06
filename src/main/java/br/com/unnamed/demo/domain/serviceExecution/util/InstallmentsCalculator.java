package br.com.unnamed.demo.domain.serviceExecution.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class InstallmentsCalculator {

    public static List<BigDecimal> calculateInstallmentsDistributed(BigDecimal totalAmount, int numberOfInstallments) {
        if (numberOfInstallments <= 0) {
            throw new IllegalArgumentException("Number of installments must be greater than 0");
        }
        if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total amount must be greater than 0");
        }

        List<BigDecimal> installments = new ArrayList<>();

        if (numberOfInstallments == 1) {
            installments.add(totalAmount);
            return installments;
        }

        // Calculate base installment amount
        BigDecimal baseInstallment = totalAmount.divide(
                BigDecimal.valueOf(numberOfInstallments),
                2,
                RoundingMode.FLOOR);

        // Calculate remainder
        BigDecimal totalBaseAmount = baseInstallment.multiply(BigDecimal.valueOf(numberOfInstallments));
        BigDecimal remainder = totalAmount.subtract(totalBaseAmount);

        // Calculate how many installments need an extra cent
        int remainderInCents = remainder.multiply(BigDecimal.valueOf(100)).intValue();

        // Distribute the installments
        for (int i = 0; i < numberOfInstallments; i++) {
            BigDecimal installmentAmount = baseInstallment;

            // Add one cent to the first 'remainderInCents' installments
            if (i < remainderInCents) {
                installmentAmount = installmentAmount.add(new BigDecimal("0.01"));
            }

            installments.add(installmentAmount);
        }

        return installments;
    }

}
