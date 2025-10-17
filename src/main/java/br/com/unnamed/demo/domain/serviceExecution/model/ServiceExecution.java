package br.com.unnamed.demo.domain.serviceExecution.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ServiceExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "service_execution_id")
    private List<ServiceExecutionChecklistItem> checklist = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;

    private String obs;

    public ServiceExecution(Tutor tutor, Pet pet, List<PetCare> petCares) {

        this.tutor = tutor;
        this.pet = pet;
        this.date = LocalDate.now();
        this.serviceStatus = ServiceStatus.PENDING;
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

    }

    public void cleanNotCheckedItems() {

        this.checklist.removeIf(Predicate.not(ServiceExecutionChecklistItem::isSelected));

    }

    public void finish() {

        this.serviceStatus = ServiceStatus.FINISHED;
        cleanNotCheckedItems();

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

    public void updateObservation(String obs) {

        this.obs = obs;

    }

    public boolean isEmpty() {

        return this.checklist.stream().filter(ServiceExecutionChecklistItem::isSelected).count() == 0;

    }

    public boolean isPending() {

        return this.serviceStatus == ServiceStatus.PENDING;

    }

    public boolean isInProgress() {

        return this.serviceStatus == ServiceStatus.IN_PROGRESS;

    }

    public boolean isDone() {

        return this.serviceStatus == ServiceStatus.DONE;

    }

    public boolean isFinished() {

        return this.serviceStatus == ServiceStatus.FINISHED;

    }

    public boolean isCancelled() {

        return this.serviceStatus == ServiceStatus.CANCELLED;

    }

    public boolean canBeUpdated() {

        return !isCancelled() && !isFinished();

    }

    public List<ServiceExecutionChecklistItem> getExecutedServices() {

        return this.checklist.stream().filter(ServiceExecutionChecklistItem::isSelected).toList();

    }

    @Override
    public String toString() {
        return "Atendimento #" + id;
    }

}
