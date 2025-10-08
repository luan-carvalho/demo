package br.com.unnamed.demo.domain.tutor.dto;

import java.util.List;

import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;

public record EditTutorDto(
                Long id,
                String name,
                String phone,
                Long groupId,
                List<Pet> pets,
                Boolean isActive) {

        public EditTutorDto(Tutor tutor) {

                this(tutor.getId(),
                                tutor.getName(),
                                tutor.getPhone(),
                                tutor.getGroup() == null ? null : tutor.getGroup().getId(),
                                tutor.getAllPets(),
                                tutor.isActive());

        }

}
