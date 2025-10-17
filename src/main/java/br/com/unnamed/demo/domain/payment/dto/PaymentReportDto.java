package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.unnamed.demo.domain.payment.model.Payment;

public record PaymentReportDto(BigDecimal amount, LocalDate date, String methodDescription) {

    public PaymentReportDto(Payment p) {

        this(p.getAmount(), p.getDate(), p.getPaymentMethod().getDescription());

    }

}
