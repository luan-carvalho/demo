package br.com.unnamed.demo.domain.tutor.model.enums;

public enum Status {
    ACTIVE,
    INACTIVE;

    public String getMessageKey() {

        return "status." + name();

    }

}
