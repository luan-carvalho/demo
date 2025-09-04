package br.com.unnamed.demo.domain.serviceExecution.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class ServiceExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @NotNull
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @NotNull
    private Tutor tutor;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ServicePaymentStatus paymentStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "service_execution_id")
    private List<ServiceExecutionItem> executedServices;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ServiceStatus serviceStatus;

    @OneToMany(mappedBy = "serviceExecution", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    public ServiceExecution() {

        this.executedServices = new ArrayList<>();
        this.date = LocalDate.now();
        this.serviceStatus = ServiceStatus.PENDING;
        this.paymentStatus = ServicePaymentStatus.NOT_PAID;

    }

    public ServiceExecution(Long id, Pet pet, Tutor tutor, ServiceStatus serviceStatus,
            List<ServiceExecutionItem> executedServices, ServicePaymentStatus paymentStatus) {

        this.id = id;
        this.pet = pet;
        this.tutor = tutor;
        this.date = LocalDate.now();
        this.serviceStatus = serviceStatus;
        this.executedServices = executedServices;
        this.paymentStatus = paymentStatus;

    }

    public void start() {

        this.serviceStatus = ServiceStatus.IN_PROGRESS;

    }

    public void finish() {

        this.serviceStatus = ServiceStatus.COMPLETED;

    }

    public void cancel() {

        this.payments.clear();
        this.serviceStatus = ServiceStatus.CANCELLED;

    }

    public void addPayment(Payment payment) {

        if (getAmountPaid().add(payment.getAmount()).compareTo(this.calculateTotal()) > 0)
            throw new IllegalArgumentException(
                    "Não é possível adicionar este pagamento, pois o serviço já foi totalmente pago");

        this.payments.add(payment);

    }

    public void removePayment(Payment payment) {

        this.payments.remove(payment);

    }

    public BigDecimal getAmountPaid() {

        return payments.stream().map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public BigDecimal getBalance() {

        return calculateTotal().subtract(getAmountPaid());

    }

    public void markAsPaid() {

        this.paymentStatus = ServicePaymentStatus.PAID;

    }

    public void addService(PetCare petCare) {

        this.executedServices.add(new ServiceExecutionItem(petCare));

    }

    public BigDecimal calculateTotal() {

        if (this.executedServices == null || this.executedServices.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return this.executedServices.stream().map(ServiceExecutionItem::getUnitPrice).reduce(BigDecimal.ZERO,
                BigDecimal::add);

    }

}
