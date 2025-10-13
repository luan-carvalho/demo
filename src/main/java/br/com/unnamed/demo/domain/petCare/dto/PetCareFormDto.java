package br.com.unnamed.demo.domain.petCare.dto;

import java.math.BigDecimal;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public record PetCareFormDto(Long id, String description, Status status, BigDecimal price, Long groupId) {

    public PetCareFormDto(PetCare petCare) {

        this(petCare.getId(), petCare.getDescription(), petCare.getStatus(), petCare.getPrice(),
                petCare.getGroup().getId());

    }

    public static PetCareFormDto empty() {

        return new PetCareFormDto(null, null, null, null, null);

    }

}
