package br.com.unnamed.demo.domain.serviceExecution.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecutionItem;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;

public record ServiceExecutionDto(
        Long id,
        Tutor tutor,
        Pet pet,
        ServiceStatus serviceStatus,
        ServicePaymentStatus paymentStatus,
        List<ServiceExecutionItem> executedServices) {

    public static ServiceExecutionDto empty() {

        return new ServiceExecutionDto(null, null, null, null, null, new ArrayList<>());

    }

}
