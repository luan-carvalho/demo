package br.com.unnamed.demo.domain.tutor.mapper;

import br.com.unnamed.demo.domain.tutor.dtos.AddressDto;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Address;

public class AddressMapper {

    public static Address toEntity(AddressDto address) {

        return new Address(
                address.zipCode(),
                address.street(),
                address.number(),
                address.neighborhood(),
                address.city(),
                address.state());

    }

    public static AddressDto toDto(Address address) {

        return new AddressDto(
                address.getZipCode(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState());

    }

}
