package br.com.unnamed.demo.domain.tutor.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PersonInfoDto(
        @NotBlank String name,
        @Valid @NotBlank String phone) {

    public static PersonInfoDto empty() {

        return new PersonInfoDto(
                null,
                null);

    }

}