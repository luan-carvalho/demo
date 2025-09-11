package br.com.unnamed.demo.domain.serviceExecution.model.enums;

public enum ServiceStatus {

    CANCELLED("Cancelado"),
    PENDING("Pendente"),
    IN_PROGRESS("Em execução"),
    DONE("Finalizado"),
    COMPLETED("Baixado");

    private final String label;

    ServiceStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
