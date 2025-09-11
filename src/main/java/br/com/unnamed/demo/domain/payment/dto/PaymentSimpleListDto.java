package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;

public record PaymentSimpleListDto(BigDecimal amount, String type) {

}
