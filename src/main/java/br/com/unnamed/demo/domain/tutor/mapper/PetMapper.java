package br.com.unnamed.demo.domain.tutor.mapper;

import br.com.unnamed.demo.domain.tutor.dtos.PetFormDto;
import br.com.unnamed.demo.domain.tutor.model.Pet;

public class PetMapper {

    public static PetFormDto toFormDto(Pet pet) {

        return new PetFormDto(
                pet.getId(),
                pet.getName(),
                pet.getStatus());

    }

    public static Pet toEntity(PetFormDto dto) {

        return new Pet(
                dto.id(),
                dto.name(),
                dto.status());

    }

}
