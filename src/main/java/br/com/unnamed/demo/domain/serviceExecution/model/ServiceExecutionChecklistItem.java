package br.com.unnamed.demo.domain.serviceExecution.model;

import java.math.BigDecimal;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class ServiceExecutionChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_care_id")
    private PetCare petCare;
    private BigDecimal unitPrice;

    private boolean selected;

    public ServiceExecutionChecklistItem() {

    }

    public ServiceExecutionChecklistItem(PetCare petCare) {

        this.petCare = petCare;
        this.unitPrice = petCare.getPrice();
        this.selected = false;

    }

    @Override
    public String toString() {
        return petCare.getDescription();
    }

    public void check() {

        this.selected = true;

    }

    public void uncheck() {

        this.selected = false;

    }

}
