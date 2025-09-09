package br.com.unnamed.demo.domain.serviceExecution.dto;

import java.util.List;

import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;

public record ServiceExecutionDto(
        Long id,
        ServiceStatus status,
        String tutorName,
        String petName,
        List<Long> selectedPetCareIds) {
}
