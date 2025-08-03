package br.com.unnamed.demo.domain.serviceExecution.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.petCare.service.PetCareService;
import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionDto;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.service.TutorService;

public class ServiceExecutionMapper {

    @Autowired
    private static TutorService tutorService;
    @Autowired
    private static PetCareService petCareService;

    public static ServiceExecutionDto toDto(ServiceExecution s) {

        return new ServiceExecutionDto(s.getId(),
                s.getTutor().getId(),
                s.getPet().getId(),
                s.getExecutedServices()
                        .stream()
                        .map(i -> i.getPetCare().getId())
                        .toList());

    }

    public static ServiceExecution toEntity(ServiceExecutionDto dto) {

        Tutor tutor = tutorService.findById(dto.tutorId());
        Pet pet = tutor.getOwnedPet(dto.petId());
        List<PetCare> petCares = dto.petCareIds().stream().map(i -> petCareService.findById(i)).toList();

        ServiceExecution s = new ServiceExecution(dto.id(), pet, tutor);
        s.updatePetCares(petCares);

        return s;

    }

}
