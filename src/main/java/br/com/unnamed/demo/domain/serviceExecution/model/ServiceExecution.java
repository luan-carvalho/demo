package br.com.unnamed.demo.domain.serviceExecution.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.PaymentStatus;
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
    private LocalDateTime arrivalTime;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;

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

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentStatus paymentStatus;

    public ServiceExecution() {

        this.executedServices = new ArrayList<>();
        this.date = LocalDate.now();
        this.arrivalTime = LocalDateTime.now();
        this.serviceStatus = ServiceStatus.PENDING;
        this.paymentStatus = PaymentStatus.NOT_PAID;

    }

    public ServiceExecution(Tutor tutor, Pet pet) {

        this.tutor = tutor;
        this.pet = pet;
        this.executedServices = new ArrayList<>();
        this.date = LocalDate.now();
        this.arrivalTime = LocalDateTime.now();
        this.serviceStatus = ServiceStatus.PENDING;
        this.paymentStatus = PaymentStatus.NOT_PAID;
        this.executedServices = new ArrayList<>();
        this.date = LocalDate.now();
        this.arrivalTime = LocalDateTime.now();
        this.serviceStatus = ServiceStatus.PENDING;
        this.paymentStatus = PaymentStatus.NOT_PAID;

    }

    public ServiceExecution(Long id, @NotNull Pet pet, @NotNull Tutor tutor) {
        
        this.id = id;
        this.pet = pet;
        this.tutor = tutor;
        this.date = LocalDate.now();
        this.arrivalTime = LocalDateTime.now();
        this.serviceStatus = ServiceStatus.PENDING;
        this.paymentStatus = PaymentStatus.NOT_PAID;

    }

    public void startService() {

        this.serviceStatus = ServiceStatus.IN_PROGRESS;
        this.startTime = LocalDateTime.now();

    }

    public void finishService() {

        this.serviceStatus = ServiceStatus.COMPLETED;
        this.finishTime = LocalDateTime.now();

    }

    public void addService(PetCare petCare) {

        this.executedServices.add(new ServiceExecutionItem(this, petCare));

    }

    public void addServices(List<PetCare> petCares) {

        petCares.forEach(p -> addService(p));

    }

    public void removeService(ServiceExecutionItem item) {

        this.executedServices.remove(item);

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
