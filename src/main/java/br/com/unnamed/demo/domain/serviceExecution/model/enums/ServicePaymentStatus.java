package br.com.unnamed.demo.domain.serviceExecution.model.enums;

public enum ServicePaymentStatus {

    PAID("Pago"),
    NOT_PAID("Não pago");

    private final String label;

    ServicePaymentStatus(String label) {

        this.label = label;

    }

    public String getLabel() {
        return label;
    }
}
