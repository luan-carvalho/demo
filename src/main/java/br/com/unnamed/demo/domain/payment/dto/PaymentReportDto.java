package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentReportDto(BigDecimal amount, LocalDate date, Long serviceExecutionId) {

}
