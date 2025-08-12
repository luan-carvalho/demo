package br.com.unnamed.demo.domain.payment.dto;

import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public record PaymentTypeDto(Long id, String description, Status status) {

    public static PaymentTypeDto empty() {

        return new PaymentTypeDto(null, null, null);

    }
    

}
