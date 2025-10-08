package br.com.unnamed.demo.domain.petCare.dtos;

import java.util.List;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;

public record PetCareGroupChecklistDto(
                Long id,
                String description,
                List<PetCare> petcares,
                long selectedCount) {

        public PetCareGroupChecklistDto(PetCareGroup entity, ServiceExecution s) {

                this(
                                entity.getId(),
                                entity.getDescription(),
                                entity.getPetcares(),
                                s.getExecutedServices()
                                                .stream()
                                                .filter(sei -> sei.getPetCare().getGroup().equals(entity))
                                                .count()

                );

        }

}
