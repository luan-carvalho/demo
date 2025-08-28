package br.com.unnamed.demo.domain.payment.mapper;

import br.com.unnamed.demo.domain.payment.dto.PaymentDto;
import br.com.unnamed.demo.domain.payment.model.Payment;

public class PaymentMapper {

    public static Payment toEntity(PaymentDto dto) {

        return new Payment(
                dto.date(),
                dto.serviceExecution(),
                dto.paymentMethod(),
                dto.amount(),
                dto.observation());

    }

    public static PaymentDto toDto(Payment p) {

        return new PaymentDto(
                p.getId(),
                p.getServiceExecution(),
                p.getAmount(),
                p.getDate(),
                p.getPaymentMethod(),
                p.getObservation());

    }

}
