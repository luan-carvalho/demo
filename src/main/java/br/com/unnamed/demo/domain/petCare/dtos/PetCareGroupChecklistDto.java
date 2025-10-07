package br.com.unnamed.demo.domain.petCare.dtos;

import java.util.List;

import br.com.unnamed.demo.domain.petCare.model.PetCare;

public record PetCareGroupChecklistDto(
        Long id,
        String description,
        List<PetCare> petcares,
        long selectedCount) {

}
