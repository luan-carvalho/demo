package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.unnamed.demo.domain.payment.model.PaymentMethod;

public record PaymentDto(
        Long id,
        String tutorName,
        String petName,
        BigDecimal amount,
        @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date,
        PaymentMethod paymentMethod,
        String observation) {

}