package br.com.unnamed.demo.domain.serviceExecution.mapper;

import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionDto;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;

public class ServiceExecutionMapper {

    public static ServiceExecutionDto toDto(ServiceExecution s) {

        return new ServiceExecutionDto(
                s.getId(),
                s.getTutor(),
                s.getPet(),
                s.getServiceStatus(),
                s.getPaymentStatus(),
                s.getExecutedServices());

    }

    public static ServiceExecution toEntity(ServiceExecutionDto dto) {

        return new ServiceExecution(dto.id(), dto.pet(), dto.tutor(), dto.serviceStatus(), dto.executedServices(),
                dto.paymentStatus());

    }

}
