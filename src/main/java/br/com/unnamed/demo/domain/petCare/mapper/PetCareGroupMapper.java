package br.com.unnamed.demo.domain.petCare.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.petCare.dtos.PetCareGroupDto;
import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;

public class PetCareGroupMapper {

    public static PetCareGroup toEntity(PetCareGroupDto dto) {

        return new PetCareGroup(dto.id(), dto.status(), dto.description());

    }

    public static PetCareGroupDto toDto(PetCareGroup entity) {

        return new PetCareGroupDto(entity.getId(), entity.getDescription(), entity.getStatus());

    }

    public static List<PetCareGroupDto> toDtoList(List<PetCareGroup> entities) {

        List<PetCareGroupDto> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(toDto(e)));

        return dtos;

    }

}
