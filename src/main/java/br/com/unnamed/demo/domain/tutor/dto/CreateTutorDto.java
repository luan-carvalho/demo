package br.com.unnamed.demo.domain.tutor.dto;

public record CreateTutorDto(String name, String phone, Long groupId) {

    public CreateTutorDto() {

        this(null, null, null);

    }

}
