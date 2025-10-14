package br.com.unnamed.demo.domain.serviceExecution.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.serviceExecution.exception.ServiceExecutionStatusException;
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
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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
    private List<ServiceExecutionChecklistItem> checklist;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ServiceStatus serviceStatus;

    private String obs;

    @OneToMany(mappedBy = "serviceExecution", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    public ServiceExecution(Tutor tutor, Pet pet, List<PetCare> petCares) {

        this.tutor = tutor;
        this.pet = pet;
        this.date = LocalDate.now();
        this.serviceStatus = ServiceStatus.PENDING;
        this.paymentStatus = ServicePaymentStatus.NOT_PAID;
        this.checklist = new ArrayList<>();
        this.payments = new ArrayList<>();
        petCares.stream().forEach(p -> checklist.add(new ServiceExecutionChecklistItem(p)));

    }

    public void start() {

        this.serviceStatus = ServiceStatus.IN_PROGRESS;

    }

    public void markAsDone() {

        this.serviceStatus = ServiceStatus.DONE;

    }

    public void cancel() {

        cleanNotCheckedItems();
        this.serviceStatus = ServiceStatus.CANCELLED;
        this.payments.stream().forEach(Payment::markAsCancelled);

    }

    public void addPayment(Payment payment) {

        if (payment.getAmount().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Não é possível adicionar um pagamento com valor negativo");

        if (payment.getAmount().compareTo(calculateTotal()) > 0)
            throw new IllegalArgumentException(
                    "Não é possível adicionar um pagamento maior que o valor total do atendimento");

        if (getBalance().compareTo(payment.getAmount()) < 0)
            throw new IllegalArgumentException(
                    "Não é possível adicionar este pagamento, pois o serviço já foi totalmente pago");

        this.payments.add(payment);

    }

    public void addPayments(List<Payment> payments) {

        payments.forEach(p -> addPayment(p));

    }

    public void updateObservation(String content) {

        this.obs = content;

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

    public void cleanNotCheckedItems() {

        this.checklist.removeIf(Predicate.not(ServiceExecutionChecklistItem::isSelected));

    }

    public void finish() {

        this.paymentStatus = ServicePaymentStatus.PAID;
        this.serviceStatus = ServiceStatus.COMPLETED;
        cleanNotCheckedItems();
        this.payments
                .stream()
                .forEach(Payment::markAsFinal);

    }

    public void addService(PetCare petCare) {

        this.checklist.add(new ServiceExecutionChecklistItem(petCare));

    }

    public BigDecimal calculateTotal() {

        if (this.checklist == null || this.checklist.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return this.checklist.stream().filter(ServiceExecutionChecklistItem::isSelected)
                .map(ServiceExecutionChecklistItem::getUnitPrice).reduce(BigDecimal.ZERO,
                        BigDecimal::add);

    }

    public void updateTutorAndPet(Tutor t, Pet p) {

        this.tutor = t;
        this.pet = p;

    }

    public void updateChecklist(List<Long> selectedItems) {

        this.checklist.stream().forEach(ServiceExecutionChecklistItem::uncheck);
        this.checklist.stream().filter(item -> selectedItems.contains(item.getId()))
                .forEach(ServiceExecutionChecklistItem::check);

    }

    public boolean isEmpty() {

        return this.checklist.stream().filter(ServiceExecutionChecklistItem::isSelected).count() == 0;

    }

    public boolean isFullyPaid() {

        return getBalance().compareTo(BigDecimal.ZERO) == 0;

    }

    public boolean canBeFinished() {

        return isFullyPaid() && isDone();

    }

    public boolean isFinished() {

        return this.serviceStatus == ServiceStatus.COMPLETED;

    }

    public boolean isDone() {

        return this.serviceStatus == ServiceStatus.DONE;

    }

    public boolean isCanceled() {

        return this.serviceStatus == ServiceStatus.CANCELLED;

    }

    public boolean canBeUpdated() {

        return !isFinished() && !isCanceled();

    }

    public void returnCompletedToDone() {

        if (this.serviceStatus != ServiceStatus.COMPLETED && !isFullyPaid())
            throw new ServiceExecutionStatusException("This service execution is not available for this operation");

        this.serviceStatus = ServiceStatus.DONE;

    }

    public List<ServiceExecutionChecklistItem> getExecutedServices() {

        return this.checklist.stream().filter(ServiceExecutionChecklistItem::isSelected).toList();

    }

}
