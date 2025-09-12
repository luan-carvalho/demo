package br.com.unnamed.demo.domain.serviceExecution.mapper;

import br.com.unnamed.demo.domain.payment.mapper.PaymentMapper;
import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionCheckoutDto;
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
                s.getExecutedServices().stream().map(ex -> ex.getPetCare().getId()).toList(),
                s.calculateTotal());

    }

    public static ServiceExecutionCheckoutDto toCheckoutDto(ServiceExecution s) {

        return new ServiceExecutionCheckoutDto(
                s.getId(),
                s.getServiceStatus(),
                s.calculateTotal(),
                s.getAmountPaid(),
                s.getBalance(),
                s.getPayments().stream().map(p -> PaymentMapper.toCheckoutListDto(p)).toList());

    }

}
