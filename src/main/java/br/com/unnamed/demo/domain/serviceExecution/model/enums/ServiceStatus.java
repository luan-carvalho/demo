package br.com.unnamed.demo.domain.serviceExecution.model.enums;

public enum ServiceStatus {

    CANCELLED,
    PENDING,
    IN_PROGRESS,
    DONE,
    FINISHED;

    public String getMessageKey() {

        return "serviceStatus." + name();

    }
}
