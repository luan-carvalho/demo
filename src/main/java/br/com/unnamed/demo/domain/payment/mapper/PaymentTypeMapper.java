package br.com.unnamed.demo.domain.payment.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.payment.dto.PaymentTypeDto;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentType;

public class PaymentTypeMapper {

    public static PaymentType toEntity(PaymentTypeDto dto) {

        return new PaymentType(dto.id(), dto.description(), dto.status());

    }

    public static PaymentTypeDto toDto(PaymentType entity) {

        return new PaymentTypeDto(entity.getId(), entity.getDescription(), entity.getStatus());

    }

    public static List<PaymentTypeDto> toDtoList(List<PaymentType> entities) {

        List<PaymentTypeDto> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(toDto(e)));

        return dtos;

    }

}
