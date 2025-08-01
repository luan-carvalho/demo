package br.com.unnamed.demo.domain.tutor.dtos;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PersonInfoDto(
        @NotBlank String name,
        @Valid AddressDto address,
        @Valid @NotBlank String phone,
        @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate birthDate) {

    public static PersonInfoDto empty() {

        return new PersonInfoDto(
                null,
                null,
                null,
                null);

    }

}