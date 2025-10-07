package br.com.unnamed.demo.domain.tutor.dtos;

import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.TutorGroup;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public record TutorFormDto(
                Long id,
                String name,
                String phone,
                TutorGroup group,
                List<Pet> pets,
                Status status) {

        public static TutorFormDto empty() {

                return new TutorFormDto(
                                null,
                                null,
                                null,
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
