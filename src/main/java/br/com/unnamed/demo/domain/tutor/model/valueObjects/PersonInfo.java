package br.com.unnamed.demo.domain.tutor.model.valueObjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class PersonInfo {

    private String name;

    @Embedded
    private Phone phone;


    public PersonInfo() {
    }

    public PersonInfo(String name, Phone phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

}
