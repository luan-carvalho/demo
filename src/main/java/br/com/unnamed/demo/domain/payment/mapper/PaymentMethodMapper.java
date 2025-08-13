package br.com.unnamed.demo.domain.payment.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.payment.dto.PaymentMethodDto;
import br.com.unnamed.demo.domain.payment.model.PaymentMethod;

public class PaymentMethodMapper {

    public static PaymentMethod toEntity(PaymentMethodDto dto) {

        return new PaymentMethod(dto.id(), dto.description(), dto.status(), dto.type());

    }

    public static PaymentMethodDto toDto(PaymentMethod entity) {

        return new PaymentMethodDto(entity.getId(), entity.getDescription(), entity.getType(), entity.getStatus());

    }

    public static List<PaymentMethodDto> toDtoList(List<PaymentMethod> entities) {

        List<PaymentMethodDto> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(toDto(e)));

        return dtos;

    }

}
