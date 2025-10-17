package br.com.unnamed.demo.domain.serviceExecution.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecutionChecklistItem;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;

public record ServiceExecutionDto(
        Long id,
        LocalDate date,
        ServiceStatus status,
        String tutorName,
        String petName,
        List<ServiceExecutionChecklistItem> checklist,
        String obs,
        BigDecimal total,
        boolean isEmpty,
        boolean canBeUpdated) {

    public ServiceExecutionDto(ServiceExecution s) {

        this(
                s.getId(),
                s.getDate(),
                s.getServiceStatus(),
                s.getTutor().getName(),
                s.getPet().getName(),
                s.getChecklist(),
                s.getObs(),
                s.calculateTotal(),
                s.isEmpty(),
                s.canBeUpdated());

    }

    public List<ServiceExecutionChecklistItem> getByGroup(PetCareGroup group) {

        return this.checklist.stream().filter(item -> item.getPetCare().getGroup().equals(group)).toList();

    }

}
