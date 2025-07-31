package br.com.unnamed.demo.domain.petCare.dtos;

import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public record PetCareGroupDto(Long id, String description, Status status) {

    public static PetCareGroupDto empty() {

        return new PetCareGroupDto(null, null, null);

    }

}
