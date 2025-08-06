package br.com.unnamed.demo.domain.serviceExecution.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @NotNull
    private LocalDateTime arrivedAt;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private LocalDateTime canceledAt;

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
        this.arrivedAt = LocalDateTime.now();
        this.serviceStatus = ServiceStatus.PENDING;

    }

    public ServiceExecution(Tutor tutor, Pet pet) {

        this.tutor = tutor;
        this.pet = pet;
        this.executedServices = new ArrayList<>();
        this.date = LocalDate.now();
        this.arrivedAt = LocalDateTime.now();
        this.serviceStatus = ServiceStatus.PENDING;

    }

    public ServiceExecution(Long id, @NotNull Pet pet, @NotNull Tutor tutor) {

        this.id = id;
        this.pet = pet;
        this.tutor = tutor;
        this.date = LocalDate.now();
        this.arrivedAt = LocalDateTime.now();
        this.serviceStatus = ServiceStatus.PENDING;
        this.executedServices = new ArrayList<>();

    }

    public void start() {

        this.serviceStatus = ServiceStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();

    }

    public void finish() {

        this.serviceStatus = ServiceStatus.COMPLETED;
        this.finishedAt = LocalDateTime.now();

    }

    public void cancel() {

        this.serviceStatus = ServiceStatus.CANCELED;
        this.canceledAt = LocalDateTime.now();

    }

    public void pay(List<Payment> payments) {

        if (getAmountPaid() == calculateTotal())
            throw new IllegalArgumentException("Service already fully paid");

        this.payments.addAll(payments);
        this.serviceStatus = ServiceStatus.PAID;

    }

    public BigDecimal getAmountPaid() {

        return payments.stream().map(Payment::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public BigDecimal getBalance() {

        return calculateTotal().subtract(getAmountPaid());

    }

    public void addService(PetCare petCare) {

        this.executedServices.add(new ServiceExecutionItem(this, petCare));

    }

    public void addServices(List<PetCare> petCares) {

        petCares.forEach(p -> addService(p));

    }

    public void updatePetCares(List<PetCare> petCares) {

        this.executedServices.clear();
        addServices(petCares);

    }

    public BigDecimal calculateTotal() {

        if (this.executedServices == null || this.executedServices.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return this.executedServices.stream().map(ServiceExecutionItem::getUnitPrice).reduce(BigDecimal.ZERO,
                BigDecimal::add);

    }

    public void updateTutorAndPet(Tutor tutor, Pet pet) {

        if (!tutor.getActivePets().contains(pet))
            throw new IllegalArgumentException("O pet informado não pertence ao tutor selecionado");

        this.tutor = tutor;
        this.pet = pet;

    }

}
