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

    public static ServiceExecutionDto toDto(ServiceExecution s) {

        return new ServiceExecutionDto(
                s.getId(),
                s.getTutor(),
                s.getPet(),
                s.getExecutedServices());

    }

    public static ServiceExecution toEntity(ServiceExecutionDto dto) {

        return new ServiceExecution(dto.id(), dto.pet(), dto.tutor(), dto.executedServices());

    }

}
