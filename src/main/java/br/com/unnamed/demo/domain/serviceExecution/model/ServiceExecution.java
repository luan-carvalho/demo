package br.com.unnamed.demo.domain.serviceExecution.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.petCare.model.PetCare;
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

    @OneToMany(mappedBy = "serviceExecution", cascade = CascadeType.ALL, orphanRemoval = true)
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

    }

    public ServiceExecution(Tutor tutor, Pet pet) {

        this.tutor = tutor;
        this.pet = pet;
        this.executedServices = new ArrayList<>();
        this.date = LocalDate.now();
        this.serviceStatus = ServiceStatus.PENDING;

    }

    public ServiceExecution(Long id, Pet pet, Tutor tutor) {

        this.id = id;
        this.pet = pet;
        this.tutor = tutor;
        this.date = LocalDate.now();
        this.serviceStatus = ServiceStatus.PENDING;
        this.executedServices = new ArrayList<>();

    }

    public void start() {

        this.serviceStatus = ServiceStatus.IN_PROGRESS;

    }

    public void finish() {

        this.serviceStatus = ServiceStatus.COMPLETED;

    }

    public void sendToRegister() {

        this.serviceStatus = ServiceStatus.IN_REGISTER;

    }

    public void cancel() {

        this.serviceStatus = ServiceStatus.CANCELED;

    }

    public void pay(List<Payment> payments) {

        if (getAmountPaid() == calculateTotal())
            throw new IllegalArgumentException("Service already fully paid");

        this.payments.addAll(payments);
        this.serviceStatus = ServiceStatus.PAID;

    }

    public void deletePayment(Payment payment) {

        this.payments.remove(payment);
        this.serviceStatus = ServiceStatus.COMPLETED;

    }

    public BigDecimal getAmountPaid() {

        return payments.stream().map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public BigDecimal getBalance() {

        return calculateTotal().subtract(getAmountPaid());

    }

    public void addService(PetCare petCare) {

        this.executedServices.add(new ServiceExecutionItem(this, petCare));

    }

    public BigDecimal calculateTotal() {

        if (this.executedServices == null || this.executedServices.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return this.executedServices.stream().map(ServiceExecutionItem::getUnitPrice).reduce(BigDecimal.ZERO,
                BigDecimal::add);

    }

}
