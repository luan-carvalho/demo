package br.com.unnamed.demo.domain.tutor.model;

import br.com.unnamed.demo.domain.tutor.model.enums.Gender;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Breed;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.CoatColor;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Specie;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specie_id")
    private Specie Specie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coat_color_id")
    private CoatColor coatColor;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Pet() {

        status = Status.ACTIVE;

    }
    public void deactivate() {

        this.status = Status.INACTIVE;

    }

    public void activate() {

        this.status = Status.ACTIVE;

    }

    public Status getStatus() {

        return status;

    }

    public boolean isActive() {

        return status == Status.ACTIVE;

    }

    public boolean isInactive() {

        return status == Status.INACTIVE;

    }

    public void updateInfo(String name,  Specie specie, Breed breed, CoatColor coatColor) {

        this.name = name;
        this.Specie = specie;
        this.breed = breed;
        this.coatColor = coatColor;

    }
}
