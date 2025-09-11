package br.com.unnamed.demo.domain.serviceExecution.mapper;

import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionDto;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;

public class ServiceExecutionMapper {

    public static ServiceExecutionDto toDto(ServiceExecution s) {

        return new ServiceExecutionDto(
                s.getId(),
                s.getDate(),
                s.getServiceStatus(),
                s.getTutor().getInfo().getName(),
                s.getPet().getName(),
                s.getExecutedServices().stream().map(ex -> ex.getPetCare().getId()).toList());

    }

}
