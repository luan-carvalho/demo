package br.com.unnamed.demo.domain.shared.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

    private String email;

    public Email() {
    }

    public Email(String email) {

        if (!isValidEmail(email))
            throw new IllegalArgumentException("Invalid phone number");

        this.email = email;

    }

    public String getValue() {
        return email;
    }

    public void setValues(String email) {
        this.email = email;
    }

    private boolean isValidEmail(String email) {

        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    }

}
