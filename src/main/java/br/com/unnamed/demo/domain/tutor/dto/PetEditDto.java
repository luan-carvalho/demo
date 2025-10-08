package br.com.unnamed.demo.domain.tutor.dto;

import br.com.unnamed.demo.domain.tutor.model.Pet;

public record PetEditDto(Long id, String name, Boolean isActive) {

    public PetEditDto(Pet pet) {

        this(pet.getId(), pet.getName(), pet.isActive());

    }

}
