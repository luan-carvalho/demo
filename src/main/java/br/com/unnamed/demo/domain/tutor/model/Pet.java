package br.com.unnamed.demo.domain.tutor.model;

import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@EqualsAndHashCode
@Getter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    public Pet() {

        status = Status.ACTIVE;

    }

    public Pet(Long id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
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

    public void updateInfo(String name) {

        this.name = name;

    }
}
