package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;

import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;

public record PaymentCheckoutListDto(Long id, PaymentMethod method, BigDecimal amount, String observation) {

}
