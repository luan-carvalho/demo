package br.com.unnamed.demo.domain.tutor.mapper;

import br.com.unnamed.demo.domain.tutor.dtos.PetFormDto;
import br.com.unnamed.demo.domain.tutor.model.Pet;

public class PetMapper {

    public static PetFormDto toFormDto(Pet pet) {

        return new PetFormDto(
            pet.getId(),
            pet.getName(),
            pet.getGender(),
            pet.getSize(),
            pet.getBirthDate(),
            pet.getSpecie(),
            pet.getBreed(),
            pet.getCoatColor(),
            pet.getStatus());

    }

    public static Pet toEntity(PetFormDto dto) {

        return new Pet(
            dto.id(),
            dto.name(),
            dto.gender(),
            dto.size(),
            dto.birthDate(),
            dto.specie(),
            dto.breed(),
            dto.coatColor(),
            dto.status());

    }
    
}
