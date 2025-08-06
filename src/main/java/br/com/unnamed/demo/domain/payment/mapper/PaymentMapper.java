package br.com.unnamed.demo.domain.payment.mapper;

import br.com.unnamed.demo.domain.payment.dto.PaymentDtos.ExistingPaymentDto;
import br.com.unnamed.demo.domain.payment.dto.PaymentDtos.PaymentDetailsDto;
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

}
