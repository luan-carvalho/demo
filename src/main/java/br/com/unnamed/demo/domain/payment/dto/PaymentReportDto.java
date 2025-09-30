package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.unnamed.demo.domain.payment.model.Payment;

public record PaymentReportDto(BigDecimal amount, LocalDate date, Long serviceExecutionId) {

    public static PaymentReportDto convert(Payment p) {

        return new PaymentReportDto(p.getAmount(), p.getDate(), p.getServiceExecution().getId());

    }

}
