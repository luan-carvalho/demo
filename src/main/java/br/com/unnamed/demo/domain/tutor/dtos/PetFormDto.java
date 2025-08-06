package br.com.unnamed.demo.domain.tutor.dtos;

import br.com.unnamed.demo.domain.tutor.model.enums.Gender;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Breed;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.CoatColor;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Specie;

public record PetFormDto(
        Long id,
        String name,
        Gender gender,
        Specie specie,
        Breed breed,
        CoatColor coatColor,
        Status status) {

    public static PetFormDto empty() {

        return new PetFormDto(null, null, null, null,  null, null, Status.ACTIVE);

    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public boolean isInactive() {
        return status == Status.INACTIVE;
    }

}