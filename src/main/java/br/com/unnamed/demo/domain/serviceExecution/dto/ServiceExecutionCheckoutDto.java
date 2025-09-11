package br.com.unnamed.demo.domain.serviceExecution.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.unnamed.demo.domain.payment.dto.PaymentCheckoutListDto;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;

public record ServiceExecutionCheckoutDto(Long id,
        ServiceStatus status,
        BigDecimal total,
        BigDecimal paid,
        BigDecimal balance,
        List<PaymentCheckoutListDto> payments) {

    public boolean isFullyPaid() {

        return balance.compareTo(BigDecimal.ZERO) == 0;

    }

}
