package br.com.unnamed.demo.domain.payment.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.PaymentMethod;
import br.com.unnamed.demo.domain.payment.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository repo;

    public PaymentService(PaymentRepository repo) {
        this.repo = repo;
    }

    public Payment findById(Long id) {

        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("No payment found with id" + id));

    }

    public List<Payment> searchWithOptionalFilters(String name, LocalDate date, PaymentMethod paymentMethod) {

        return repo.searchWithOptionalFilters(name, paymentMethod, date);

    }

}
