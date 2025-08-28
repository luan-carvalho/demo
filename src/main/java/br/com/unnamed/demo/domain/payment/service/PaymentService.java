package br.com.unnamed.demo.domain.payment.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.payment.repository.PaymentMethodRepository;
import br.com.unnamed.demo.domain.payment.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository payRepo;
    private final PaymentMethodRepository payMethodRepo;

    public PaymentService(PaymentRepository payRepo, PaymentMethodRepository payMethodRepo) {
        this.payRepo = payRepo;
        this.payMethodRepo = payMethodRepo;
    }

    public List<PaymentMethod> getAllPaymentMethods() {

        return payMethodRepo.findAll();

    }

    public Payment findById(Long id) {

        return payRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No payment found with id" + id));

    }

    public List<Payment> searchWithOptionalFilters(String name, LocalDate date, PaymentMethod paymentMethod) {

        return payRepo.searchWithOptionalFilters(name, paymentMethod, date);

    }

}
