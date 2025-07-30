package br.com.unnamed.demo.domain.tutor.dtos;

public record AddressDto (
                String zipCode,
                String street,
                String number,
                String neighborhood,
                String city,
                String state) {
}
