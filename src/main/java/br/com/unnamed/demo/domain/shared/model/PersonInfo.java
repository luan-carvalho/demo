package br.com.unnamed.demo.domain.shared.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class PersonInfo {

    private String name;

    @Embedded
    private Phone phone;

    @Embedded
    private Email email;

    @Embedded
    private Address address;

    private LocalDate birthDate;

    public PersonInfo() {
    }

    public PersonInfo(String name, Phone phone, Email email, Address address, LocalDate birthDate) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public long getAge() {

        return ChronoUnit.YEARS.between(this.birthDate, LocalDate.now());

    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Email getEmail() {
        return email;
    }

}
