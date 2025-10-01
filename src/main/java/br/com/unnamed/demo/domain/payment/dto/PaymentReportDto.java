package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.unnamed.demo.domain.payment.model.Payment;

public record PaymentReportDto(BigDecimal amount, String tutorName, String petName, LocalDate date,
        Long serviceExecutionId, String methodDescription) {

    public static PaymentReportDto convert(Payment p) {

        return new PaymentReportDto(p.getAmount(), p.getTutorName(), p.getPetName(), p.getDate(),
                p.getServiceExecution().getId(), p.getPaymentMethod().getDescription());

    }

}
