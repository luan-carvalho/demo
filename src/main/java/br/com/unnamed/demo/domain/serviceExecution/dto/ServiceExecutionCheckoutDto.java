package br.com.unnamed.demo.domain.serviceExecution.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.unnamed.demo.domain.payment.dto.PaymentCheckoutListDto;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;

public record ServiceExecutionCheckoutDto(Long id,
        ServiceStatus status,
        BigDecimal total,
        BigDecimal paid,
        BigDecimal balance,
        boolean isFullyPaid,
        boolean canBeFinished,
        List<PaymentCheckoutListDto> payments) {

    public ServiceExecutionCheckoutDto(ServiceExecution serviceExecution) {

        this(
                serviceExecution.getId(),
                serviceExecution.getServiceStatus(),
                serviceExecution.calculateTotal(),
                serviceExecution.getAmountPaid(),
                serviceExecution.getBalance(),
                serviceExecution.isFullyPaid(),
                serviceExecution.canBeFinished(),
                serviceExecution.getPayments().stream().map(p -> new PaymentCheckoutListDto(p)).toList());

    }

}
