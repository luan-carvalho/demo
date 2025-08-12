package br.com.unnamed.demo.domain.payment.mapper;

import br.com.unnamed.demo.domain.payment.dto.PaymentDtos.ExistingPaymentDto;
import br.com.unnamed.demo.domain.payment.dto.PaymentDtos.PaymentDetailsDto;
import br.com.unnamed.demo.domain.payment.dto.PaymentDtos.PaymentDto;
import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;

public class PaymentMapper {

    public static PaymentDetailsDto toPaymentDetailDto(ServiceExecution execution) {

        PaymentDetailsDto dto = new PaymentDetailsDto();
        dto.setTotalAmount(execution.calculateTotal());
        dto.setPaidAmount(execution.getAmountPaid());
        dto.setBalance(execution.getBalance());
        dto.setPayments(execution.getPayments().stream().map(p -> toExistingPaymentDto(p)).toList());

        return dto;

    }

    public static ExistingPaymentDto toExistingPaymentDto(Payment payment) {

        return new ExistingPaymentDto(payment.getValue(), payment.getDate().toString(),
                payment.getType().getDescription());

    }

    public static Payment toEntity(PaymentDto dto) {

        return new Payment(dto.getDate(), dto.getType(), dto.getValue(), dto.getServiceExecution(),
                dto.getStatus());

    }

    public static PaymentDto toDto(Payment p) {

        return new PaymentDto(p.getId(), p.getServiceExecution(), p.getValue(), p.getDate(), p.getType(),
                p.getStatus());

    }

}
