package br.com.unnamed.demo.domain.serviceExecution.builder;

import java.time.LocalDate;
import java.util.List;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecutionItem;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import lombok.Getter;

@Getter
public class ServiceExecutionBuilder {

    private Long id;
    private Tutor tutor;
    private Pet pet;
    private LocalDate date;
    private ServiceStatus status;
    private List<ServiceExecutionItem> items;
    private List<Payment> payments;
    private ServicePaymentStatus paymentStatus;

    public ServiceExecutionBuilder() {
    }

    public ServiceExecutionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ServiceExecutionBuilder tutor(Tutor tutor) {
        this.tutor = tutor;
        return this;
    }

    public ServiceExecutionBuilder pet(Pet pet) {
        this.pet = pet;
        return this;
    }

    public ServiceExecutionBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public ServiceExecutionBuilder status(ServiceStatus status) {
        this.status = status;
        return this;
    }

    public ServiceExecutionBuilder items(List<ServiceExecutionItem> items) {
        this.items = items;
        return this;
    }

    public ServiceExecutionBuilder payments(List<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public ServiceExecutionBuilder paymentStatus(ServicePaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public ServiceExecution build() {
        return new ServiceExecution(this);
    }

}
