package br.com.unnamed.demo.domain.petCare.dto;

import java.math.BigDecimal;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import jakarta.validation.constraints.NotNull;

public record PetCareFormDto(
        Long id,
        @NotNull(message = "É necessário informar a descrição do serviço") String description,
        @NotNull(message = "É necessário informar o preço do serviço") BigDecimal price,
        Status status,
        Long groupId) {

    public PetCareFormDto(PetCare petcare) {

        this(petcare.getId(), petcare.getDescription(), petcare.getPrice(), petcare.getStatus(),
                petcare.getGroup() == null ? null : petcare.getGroup().getId());

    }

    public static PetCareFormDto empty() {

        return new PetCareFormDto(null, null, null, null, null);

    }

}
