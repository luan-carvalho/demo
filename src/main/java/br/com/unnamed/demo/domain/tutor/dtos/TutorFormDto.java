package br.com.unnamed.demo.domain.tutor.dtos;

import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.TutorGroup;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import jakarta.validation.Valid;

public record TutorFormDto(
                Long id,
                @Valid PersonInfoDto info,
                TutorGroup group,
                List<Pet> pets,
                Status status) {

        public static TutorFormDto empty() {

                PersonInfoDto info = new PersonInfoDto(
                                null,
                                null);

                return new TutorFormDto(
                                null,
                                info,
                                null,
                                new ArrayList<>(),
                                null);

        }

        public boolean isActive() {
                return status == Status.ACTIVE;
        }

        public boolean isInactive() {
                return status == Status.INACTIVE;
        }

}
