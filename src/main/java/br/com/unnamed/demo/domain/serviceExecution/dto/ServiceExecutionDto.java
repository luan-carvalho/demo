package br.com.unnamed.demo.domain.serviceExecution.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;

public record ServiceExecutionDto(
        Long id,
        Tutor tutor,
        Pet pet,
        LocalDate date,
        List<Long> selectedPetCareIds,
        ServiceStatus status,
        List<Payment> payments,
        ServicePaymentStatus paymentStatus) {

    public static ServiceExecutionDto empty() {

        return new ServiceExecutionDto(null, null, null, LocalDate.now(), new ArrayList<>(), ServiceStatus.PENDING,
                new ArrayList<>(),
                ServicePaymentStatus.NOT_PAID);

    }

}
