package br.com.unnamed.demo.domain.shared.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Phone {

    private String phone;

    public Phone() {
    }

    public Phone(String phone) {

        if (!isValidPhoneNumber(phone))
            throw new IllegalArgumentException("Invalid phone number");

        this.phone = phone;
    }

    public String getValue() {
        return phone;
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^\\(?\\d{2}\\)?\\s?(9?\\d{4})-?\\d{4}$");
    }

    public void setValue(String phone) {
        this.phone = phone;
    }

}
