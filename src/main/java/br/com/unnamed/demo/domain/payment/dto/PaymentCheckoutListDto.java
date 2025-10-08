package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;

public record PaymentCheckoutListDto(Long id, PaymentMethod method, BigDecimal amount) {

    public PaymentCheckoutListDto(Payment p) {

        this(
                p.getId(),
                p.getPaymentMethod(),
                p.getAmount());

    }

}
