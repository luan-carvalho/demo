package br.com.unnamed.demo.domain.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.unnamed.demo.domain.payment.model.enums.PaymentStatus;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentType;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class PaymentDtos {

    @Getter
    @AllArgsConstructor
    public static class PaymentDto {

        private Long id;
        private ServiceExecution serviceExecution;
        private BigDecimal value;
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        private LocalDate date;
        private PaymentType type;
        private PaymentStatus status;

        public boolean isActive() {

            return this.status == PaymentStatus.ACTIVE;

        }

    }

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