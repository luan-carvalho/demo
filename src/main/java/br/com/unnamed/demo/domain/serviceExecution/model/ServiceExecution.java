package br.com.unnamed.demo.domain.serviceExecution.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ServiceExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime arrivalTime;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private LocalDateTime pickUpTime;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @OneToMany(mappedBy = "serviceExecution")
    private List<ServiceExecutionItem> executedServices;

    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    public ServiceExecution() {

    }

    public ServiceExecution(Tutor tutor, Pet pet) {

        this.tutor = tutor;
        this.pet = pet;
        this.executedServices = new ArrayList<>();
        this.total = BigDecimal.ZERO;
        this.serviceStatus = ServiceStatus.PENDING;

    }

    public void startService() {

        this.serviceStatus = ServiceStatus.IN_PROGRESS;

    }

    public void finishService() {

        this.serviceStatus = ServiceStatus.COMPLETED;
        this.finishTime = LocalDateTime.now();

    }

    public void addService(PetCare petCare, int quantity) {

        this.executedServices.add(new ServiceExecutionItem(this, petCare, quantity));

    }

    public void removeService(ServiceExecutionItem item) {

        this.executedServices.remove(item);

    }

}
