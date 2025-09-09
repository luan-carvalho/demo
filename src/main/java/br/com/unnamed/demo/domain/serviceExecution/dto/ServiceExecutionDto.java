package br.com.unnamed.demo.domain.serviceExecution.dto;

import java.util.ArrayList;
import java.util.List;

public record ServiceExecutionDto(
        Long id,
        String tutorName,
        String petName,
        List<Long> selectedPetCareIds) {

    public static ServiceExecutionDto empty() {

        return new ServiceExecutionDto(null, null, null, new ArrayList<>());

    }

}
