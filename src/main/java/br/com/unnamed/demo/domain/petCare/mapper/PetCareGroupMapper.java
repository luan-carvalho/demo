package br.com.unnamed.demo.domain.petCare.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.petCare.dtos.PetCareGroupChecklistDto;
import br.com.unnamed.demo.domain.petCare.dtos.PetCareGroupDto;
import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;

public class PetCareGroupMapper {

    public static PetCareGroup toEntity(PetCareGroupDto dto) {

        return new PetCareGroup(dto.id(), dto.status(), dto.description());

    }

    public static PetCareGroupDto toDto(PetCareGroup entity) {

        return new PetCareGroupDto(entity.getId(), entity.getDescription(), entity.getStatus());

    }

    public static PetCareGroupChecklistDto toCheckListDto(PetCareGroup entity, ServiceExecution s) {

        return new PetCareGroupChecklistDto(
                entity.getId(),
                entity.getDescription(),
                entity.getPetcares(),
                s.getExecutedServices()
                        .stream()
                        .filter(sei -> sei.getPetCare().getGroup().equals(entity))
                        .count()

        );

    }

    public static List<PetCareGroupDto> toDtoList(List<PetCareGroup> entities) {

        List<PetCareGroupDto> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(toDto(e)));

        return dtos;

    }

}
