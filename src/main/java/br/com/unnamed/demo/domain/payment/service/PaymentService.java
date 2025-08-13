package br.com.unnamed.demo.domain.payment.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.PaymentMethod;
import br.com.unnamed.demo.domain.payment.repository.PaymentRepository;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.repository.ServiceExecutionRepository;

@Service
public class PaymentService {

    private final PaymentRepository repo;
    private final ServiceExecutionRepository serviceRepo;

    public PaymentService(PaymentRepository repo, ServiceExecutionRepository serviceRepo) {
        this.repo = repo;
        this.serviceRepo = serviceRepo;
    }

    public Payment findById(Long id) {

        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("No payment found with id" + id));

    }

    public List<Payment> searchWithOptionalFilters(String name, LocalDate date, PaymentMethod paymentMethod) {

        return repo.searchWithOptionalFilters(name, paymentMethod, date);

    }

    public void save(Payment p) {

        repo.save(p);

    }

    public void delete(Long id) {

        Payment p = findById(id);
        ServiceExecution s = p.getServiceExecution();
        s.deletePayment(p);
        serviceRepo.save(s);
        repo.save(p);

    }

}
