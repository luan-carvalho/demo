package br.com.unnamed.demo.domain.tutor.model.valueObjects;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import br.com.unnamed.demo.authentication.model.valueObjects.Email;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class PersonInfo {

    private String name;

    @Embedded
    private Phone phone;

    @Embedded
    private Address address;

    private LocalDate birthDate;

    public PersonInfo() {
    }

    public PersonInfo(String name, Phone phone, Address address, LocalDate birthDate) {
        this.name = name;
        this.phone = phone;
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

}
