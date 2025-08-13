package br.com.unnamed.demo.domain.payment.dto;

import br.com.unnamed.demo.domain.payment.model.enums.PaymentMethodType;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public record PaymentMethodDto(Long id, String description, PaymentMethodType type, Status status) {

    public static PaymentMethodDto empty() {

        return new PaymentMethodDto(null, null, null, null);

    }

}
