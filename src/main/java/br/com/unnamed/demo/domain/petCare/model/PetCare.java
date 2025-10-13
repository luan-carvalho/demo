package br.com.unnamed.demo.domain.petCare.model;

import java.math.BigDecimal;

import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class PetCare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private PetCareGroup group;

    @Enumerated(EnumType.STRING)
    private Status status;

    public PetCare() {

        status = Status.ACTIVE;

    }

    public PetCare(String description, BigDecimal price, PetCareGroup group) {
        this.description = description;
        this.price = price;
        this.group = group;
        this.status = Status.ACTIVE;
    }

    public void updateDescription(String description) {

        if (description.isBlank() || description == null)
            throw new IllegalArgumentException("Cannot update the description to a blank or null value.");

        this.description = description;

    }

    public void updatePrice(BigDecimal price) {

        if (price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Cannot update the price to a negative or 0 value.");

        this.price = price;

    }

    public void updateGroup(PetCareGroup group) {

        this.group = group;

    }

    public boolean belongsToGroup(PetCareGroup group) {

        return this.group.equals(group);

    }

    public boolean isActive() {

        return status == Status.ACTIVE;

    }

    public boolean isInactive() {

        return status == Status.INACTIVE;

    }

    public void deactivate() {

        this.status = Status.INACTIVE;

    }

    public void activate() {

        this.status = Status.ACTIVE;

    }

    @Override
    public String toString() {
        return "PetCare [id=" + id + ", description=" + description + ", price=" + price + "]";
    }

}
