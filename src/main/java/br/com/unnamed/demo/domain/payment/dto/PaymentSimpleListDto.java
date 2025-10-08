package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;

import br.com.unnamed.demo.domain.payment.model.Payment;

public record PaymentSimpleListDto(BigDecimal amount, String type) {

    public PaymentSimpleListDto(Payment payment) {

        this(payment.getAmount(), payment.getPaymentMethod().getDescription());

    }

}
