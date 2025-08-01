package br.com.unnamed.demo.domain.tutor.model.valueObjects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String zipCode;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;

    public Address() {
    }

    public Address(String zipCode, String street, String number, String neighborhood, String city, String state) {
        this.zipCode = zipCode;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

}
