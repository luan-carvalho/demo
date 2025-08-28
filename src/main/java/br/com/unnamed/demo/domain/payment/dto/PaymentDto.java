package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;

public record PaymentDto(
                Long id,
                ServiceExecution serviceExecution,
                BigDecimal amount,
                @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date,
                PaymentMethod paymentMethod,
                String observation) {

}