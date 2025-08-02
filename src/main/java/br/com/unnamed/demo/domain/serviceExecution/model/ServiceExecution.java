package br.com.unnamed.demo.domain.serviceExecution.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
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

@Entity
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
    private LocalDateTime pickUpTime;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @NotNull
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @NotNull
    private Tutor tutor;

    @OneToMany(mappedBy = "serviceExecution")
    private List<ServiceExecutionItem> executedServices;

    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;

    public ServiceExecution() {

    }

    public ServiceExecution(Tutor tutor, Pet pet) {

        this.tutor = tutor;
        this.pet = pet;
        this.executedServices = new ArrayList<>();
        this.date = LocalDate.now();
        this.arrivalTime = LocalDateTime.now();
        this.serviceStatus = ServiceStatus.PENDING;

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

    public void removeService(ServiceExecutionItem item) {

        this.executedServices.remove(item);

    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public LocalDateTime getPickUpTime() {
        return pickUpTime;
    }

    public Pet getPet() {
        return pet;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public List<ServiceExecutionItem> getExecutedServices() {
        return executedServices;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }
}
