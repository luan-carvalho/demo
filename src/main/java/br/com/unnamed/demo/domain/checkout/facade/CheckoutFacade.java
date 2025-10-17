package br.com.unnamed.demo.domain.checkout.facade;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.checkout.model.Checkout;
import br.com.unnamed.demo.domain.checkout.service.CheckoutService;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.payment.service.PaymentService;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;

@Service
public class CheckoutFacade {

    // private final TutorService tutorService;
    // private final ServiceExecutionService service;
    private final CheckoutService checkoutService;
    private final PaymentService paymentService;
    // private final PetCarePackageClientService packageService;

    // public CheckoutFacade(TutorService tutorService, ServiceExecutionService
    // service, CheckoutService checkoutService,
    // PetCarePackageClientService packageService) {
    // this.tutorService = tutorService;
    // this.service = service;
    // this.checkoutService = checkoutService;
    // this.packageService = packageService;
    // }

    public CheckoutFacade(CheckoutService checkoutService, PaymentService paymentService) {
        this.checkoutService = checkoutService;
        this.paymentService = paymentService;
    }

    public Checkout findById(Long checkoutId) {

        return checkoutService.findById(checkoutId);

    }

    public Checkout addItemToCheckout(ServiceExecution s) {

        return checkoutService.addItem(s);

    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentService.getAllPaymentMethods();
    }

    public void addPayment(Long checkoutId, Long methodId, BigDecimal amount, Integer installments) {

        PaymentMethod method = paymentService.findPaymentMethodById(methodId);

        checkoutService.addPayment(checkoutId, method, amount, installments);

    }

}
