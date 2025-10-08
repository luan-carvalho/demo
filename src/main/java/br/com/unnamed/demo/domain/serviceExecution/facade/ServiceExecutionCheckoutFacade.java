package br.com.unnamed.demo.domain.serviceExecution.facade;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.payment.service.PaymentService;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.service.ServiceExecutionService;
import br.com.unnamed.demo.domain.serviceExecution.util.InstallmentsCalculator;

@Service
public class ServiceExecutionCheckoutFacade {

    private final ServiceExecutionService service;
    private final PaymentService paymentService;

    public ServiceExecutionCheckoutFacade(ServiceExecutionService service, PaymentService paymentService) {
        this.service = service;
        this.paymentService = paymentService;
    }

    public ServiceExecution findServiceExecutionById(Long serviceExecutionid) {

        return service.findById(serviceExecutionid);

    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentService.getAllPaymentMethods();
    }

    public void addPayment(Long serviceExecutionId, Long typeId, BigDecimal amount, Integer installments) {

        PaymentMethod method = paymentService.findPaymentMethodById(typeId);

        if (installments.compareTo(1) == 0) {

            service.addPayment(serviceExecutionId, new Payment(method, amount));
            return;

        }

        List<Payment> payments = InstallmentsCalculator
                .calculateInstallmentsDistributed(amount, installments)
                .stream()
                .map(a -> new Payment(method, a)).toList();

        service.addPayments(serviceExecutionId, payments);

    }

    public void finishServiceExecution(Long serviceExecutionId) {

        service.finish(serviceExecutionId);

    }

    public void removePayment(Long serviceExecutionId, Long paymentId) {

        paymentService.checkIfPaymentBelongsToServiceExecution(serviceExecutionId, paymentId);
        Payment p = paymentService.findById(paymentId);
        service.removePayment(serviceExecutionId, p);

    }

    public void updatePaymentInfo(Long serviceExecutionId, Long paymentId, Long methodId, BigDecimal amount) {

        paymentService.checkIfPaymentBelongsToServiceExecution(serviceExecutionId, paymentId);
        paymentService.updatePaymentInfo(paymentId, methodId, amount);

    }

}
