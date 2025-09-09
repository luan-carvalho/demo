package br.com.unnamed.demo.domain.serviceExecution.mapper;

import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionDto;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;

public class ServiceExecutionMapper {

    public static ServiceExecutionDto toDto(ServiceExecution s) {

        return new ServiceExecutionDto(
                s.getId(),
                s.getTutor(),
                s.getPet(),
                s.getDate(),
                s.getExecutedServices().stream().map(ex -> ex.getPetCare().getId()).toList(),
                s.getServiceStatus(),
                s.getPayments(),
                s.getPaymentStatus());

    }

}
