package br.com.unnamed.demo.domain.tutor.dto;

import java.util.List;

import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;

public record TutorGridDto(
                Long id,
                String name,
                List<Pet> pets) {

        public TutorGridDto(Tutor tutor) {

                this(tutor.getId(), tutor.getName(), tutor.getActivePets());

        }
}
