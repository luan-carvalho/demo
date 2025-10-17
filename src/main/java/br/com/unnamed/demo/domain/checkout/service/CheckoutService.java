package br.com.unnamed.demo.domain.checkout.service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.checkout.model.Checkout;
import br.com.unnamed.demo.domain.checkout.repository.CheckoutRepository;
import br.com.unnamed.demo.domain.checkout.util.InstallmentsCalculator;
import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.tutor.model.Tutor;

@Service
public class CheckoutService {

    private final CheckoutRepository repo;

    public CheckoutService(CheckoutRepository repo) {
        this.repo = repo;
    }

    public Checkout create(Tutor tutor) {

        Checkout c = new Checkout(tutor);
        return repo.save(c);

    }

    public Checkout findById(Long checkoutId) {

        return repo.findById(checkoutId)
                .orElseThrow(() -> new NoSuchElementException("There is no checkout open with id " + checkoutId));

    }

    public Checkout getOpenCheckoutFromTutor(Tutor tutor) {

        return repo.findByTutor(tutor);

    }

    public Checkout addItem(ServiceExecution s) {

        Checkout c = getOpenCheckoutFromTutor(s.getTutor());

        if (c == null) {

            c = create(s.getTutor());

        }

        c.addItem(s);
        return repo.save(c);

    }

    public void addPayment(Long checkoutId, PaymentMethod method, BigDecimal amount, Integer installments) {

        Checkout c = findById(checkoutId);

        if (installments.compareTo(1) == 0) {

            c.addPayment(new Payment(c, method, amount));
            repo.save(c);
            return;

        }

        c.addPayments(InstallmentsCalculator
                .calculateInstallmentsDistributed(amount, installments)
                .stream()
                .map(a -> new Payment(c, method, a)).toList());

        repo.save(c);

    }

}
