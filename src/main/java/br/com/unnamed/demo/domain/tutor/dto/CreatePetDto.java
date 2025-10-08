package br.com.unnamed.demo.domain.tutor.dto;

public record CreatePetDto(String name) {

    public CreatePetDto() {

        this(null);

    }

}
