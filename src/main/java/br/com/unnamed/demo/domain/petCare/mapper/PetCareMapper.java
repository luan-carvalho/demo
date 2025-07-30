package br.com.unnamed.demo.domain.petCare.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.petCare.dtos.PetCareDto;
import br.com.unnamed.demo.domain.petCare.model.PetCare;

public class PetCareMapper {

    public static PetCare toEntity(PetCareDto dto) {

        return new PetCare(dto.id(), dto.description(), dto.price(), dto.group(),
                dto.status());

    }

    public static PetCareDto toDto(PetCare entity) {

        return new PetCareDto(entity.getId(), entity.getDescription(), entity.getStatus(), entity.getPrice(),
                entity.getGroup());

    }

    public static List<PetCareDto> toDtoList(List<PetCare> entities) {

        List<PetCareDto> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(toDto(e)));

        return dtos;

    }

}
