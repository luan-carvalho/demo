package br.com.unnamed.demo.domain.serviceExecution.model;

import java.math.BigDecimal;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ServiceExecutionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_execution_id")
    private ServiceExecution serviceExecution;

    @ManyToOne
    @JoinColumn(name = "pet_care_id")
    private PetCare petCare;
    private BigDecimal unitPrice;

    public ServiceExecutionItem() {

    }

    public ServiceExecutionItem(ServiceExecution serviceExecution, PetCare petCare) {
        this.serviceExecution = serviceExecution;
        this.petCare = petCare;
        this.unitPrice = petCare.getPrice();
    }

    public Long getId() {
        return id;
    }

    public PetCare getPetCare() {
        return petCare;
    }

    public ServiceExecution getServiceExecution() {
        return serviceExecution;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return petCare.getDescription();
    }

    

}
