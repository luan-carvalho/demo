package br.com.unnamed.demo.domain.payment.mapper;

import br.com.unnamed.demo.domain.payment.dto.PaymentCheckoutListDto;
import br.com.unnamed.demo.domain.payment.dto.PaymentDto;
import br.com.unnamed.demo.domain.payment.dto.PaymentSimpleListDto;
import br.com.unnamed.demo.domain.payment.model.Payment;

public class PaymentMapper {

    public static PaymentDto toDto(Payment p) {

        return new PaymentDto(
                p.getId(),
                p.getServiceExecution(),
                p.getAmount(),
                p.getDate(),
                p.getPaymentMethod(),
                p.getObservation());

    }

    public static PaymentCheckoutListDto toCheckoutListDto(Payment p) {

        return new PaymentCheckoutListDto(
                p.getId(),
                p.getPaymentMethod(),
                p.getAmount(),
                p.getObservation());

    }

    public static PaymentSimpleListDto toSimpleListDto(Payment p) {

        return new PaymentSimpleListDto(p.getAmount(), p.getPaymentMethod().getDescription());

    }

}
