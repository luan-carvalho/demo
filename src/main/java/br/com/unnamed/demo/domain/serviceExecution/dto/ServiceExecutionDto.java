package br.com.unnamed.demo.domain.serviceExecution.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;

public record ServiceExecutionDto(
        Long id,
        LocalDate date,
        ServiceStatus status,
        String tutorName,
        String petName,
        List<Long> selectedPetCareIds,
        String obs,
        BigDecimal total) {
}
