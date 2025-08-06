package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class PaymentDtos {

    @Getter
    @AllArgsConstructor
    public static class ExistingPaymentDto {
        private BigDecimal amount;
        private String paymentDate;
        private String paymentType;
    }

    @Getter
    @Setter
    public static class PaymentItemDto {
        private BigDecimal amount;
        private Long paymentTypeId;
    }

    @Getter
    @Setter
    public static class PaymentRequestDto {
        private List<PaymentItemDto> payments;
    }

    @Getter
    @Setter
    public static class PaymentDetailsDto {
        private BigDecimal totalAmount;
        private BigDecimal paidAmount;
        private BigDecimal balance;
        private List<ExistingPaymentDto> payments;
    }
}