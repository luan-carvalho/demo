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
    @JoinColumn(name = "petCare_id")
    private PetCare petCare;
    private BigDecimal unitPrice;
    private int quantity;

    public ServiceExecutionItem() {

    }

    public ServiceExecutionItem(ServiceExecution serviceExecution, PetCare petCare, int quantity) {
        this.serviceExecution = serviceExecution;
        this.petCare = petCare;
        this.unitPrice = petCare.getPrice();
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public PetCare getPetCare() {
        return petCare;
    }

    public BigDecimal calculateTotalPrice() {

        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalStateException("Price must not be null and greater than zero");

        if (quantity <= 0)
            throw new IllegalStateException("Quantity must be greater than zero");

        return unitPrice.multiply(BigDecimal.valueOf(quantity));

    }

}
