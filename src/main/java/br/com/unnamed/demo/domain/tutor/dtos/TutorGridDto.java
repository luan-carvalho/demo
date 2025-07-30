package br.com.unnamed.demo.domain.tutor.dtos;

import java.util.List;

import br.com.unnamed.demo.domain.tutor.model.Pet;

public record TutorGridDto (
        Long id,
        String name,
        List<Pet> pets) {
}
