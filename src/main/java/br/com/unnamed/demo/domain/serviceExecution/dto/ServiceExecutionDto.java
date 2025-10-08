package br.com.unnamed.demo.domain.serviceExecution.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.unnamed.demo.domain.payment.dto.PaymentSimpleListDto;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;

public record ServiceExecutionDto(
        Long id,
        LocalDate date,
        ServiceStatus status,
        String tutorName,
        String petName,
        List<Long> selectedPetCareIds,
        List<PaymentSimpleListDto> payments,
        String obs,
        BigDecimal total) {

    public ServiceExecutionDto(ServiceExecution s) {

        this(
                s.getId(),
                s.getDate(),
                s.getServiceStatus(),
                s.getTutor().getName(),
                s.getPet().getName(),
                s.getExecutedServices().stream().map(ex -> ex.getPetCare().getId()).toList(),
                s.getPayments().stream().map(p -> new PaymentSimpleListDto(p)).toList(),
                s.getObs(),
                s.calculateTotal());

    }

}
