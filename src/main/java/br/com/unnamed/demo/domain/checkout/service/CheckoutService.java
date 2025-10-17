package br.com.unnamed.demo.domain.checkout.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.checkout.model.Checkout;
import br.com.unnamed.demo.domain.checkout.repository.CheckoutRepository;
import br.com.unnamed.demo.domain.payment.model.Payment;
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

    public void addPayment(Long checkoutId, Payment payment) {

        Checkout c = findById(checkoutId);
        c.addPayment(payment);
        repo.save(c);

    }

    public void addPayments(Long checkoutId, List<Payment> payments) {

        Checkout c = findById(checkoutId);
        c.addPayments(payments);
        repo.save(c);

    }

}
