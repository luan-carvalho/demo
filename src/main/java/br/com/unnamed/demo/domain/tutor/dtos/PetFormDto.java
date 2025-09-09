package br.com.unnamed.demo.domain.tutor.dtos;

import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public record PetFormDto(
        Long id,
        String name,
        Status status) {

    public static PetFormDto empty() {

        return new PetFormDto(null, null,  Status.ACTIVE);

    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public boolean isInactive() {
        return status == Status.INACTIVE;
    }

}