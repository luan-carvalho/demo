package br.com.unnamed.demo.domain.petCare.model;

import java.util.List;

import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class PetCareGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PetCare> petcares;

    public PetCareGroup() {

        status = Status.ACTIVE;

    }

    public PetCareGroup(Long id, Status status, String description) {
        this.id = id;
        this.status = status;
        this.description = description;
    }

    public void updateDescription(String description) {
        this.description = description;
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

}
