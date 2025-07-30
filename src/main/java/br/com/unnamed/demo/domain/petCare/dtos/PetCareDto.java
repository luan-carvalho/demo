package br.com.unnamed.demo.domain.petCare.dtos;

import java.math.BigDecimal;

import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public record PetCareDto(Long id, String description, Status status, BigDecimal price, PetCareGroup group) {

    public static PetCareDto empty() {

        return new PetCareDto(null, null, null, BigDecimal.ZERO, null);

    }

}
