package br.com.unnamed.demo.domain.serviceExecution.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.tutor.model.Pet;
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

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime pickedUpTime;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToMany(mappedBy = "serviceExecution")
    private List<ServiceExecutionItem> executedServices;

    @Enumerated(EnumType.STRING)
    private ServiceStatus status;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    public ServiceExecution() {

        executedServices = new ArrayList<>();
        total = BigDecimal.ZERO;
        status = ServiceStatus.AWAITING_START;

    }

    public BigDecimal calculateTotal() {

        return executedServices
                .stream()
                .map(ServiceExecutionItem::calculateTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

}
