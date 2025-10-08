package br.com.unnamed.demo.domain.serviceExecution.exception;

public class PaymentDoesntBelongtoServiceExecutionException extends RuntimeException {

    public PaymentDoesntBelongtoServiceExecutionException(String message) {

        super(message);

    }

}
