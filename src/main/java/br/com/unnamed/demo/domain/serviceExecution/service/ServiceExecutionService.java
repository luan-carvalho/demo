package br.com.unnamed.demo.domain.serviceExecution.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.serviceExecution.repository.ServiceExecutionRepository;

@Service
public class ServiceExecutionService {

    private ServiceExecutionRepository repo;

    public ServiceExecutionService(ServiceExecutionRepository repo) {
        this.repo = repo;
    }

    public ServiceExecution findById(Long id) {

        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Service execution not found"));

    }

    public void save(ServiceExecution serviceExecution) {

        repo.save(serviceExecution);

    }

    public Page<ServiceExecution> searchWithOptionalFilters(String name, LocalDate date, ServiceStatus status,
            ServicePaymentStatus paymentStatus, Pageable pageable) {

        return repo.findWithOptionalFilters(name, date, status, paymentStatus, pageable);

    }

    public List<ServiceExecution> findByStatusAndDate(ServiceStatus status, LocalDate date) {

        return repo.findByStatusAndDate(status, date);

    }

    public void start(ServiceExecution s) {

        s.start();
        repo.save(s);

    }

    public void finish(ServiceExecution s) {

        s.finish();
        repo.save(s);

    }

    public void cancel(ServiceExecution s) {

        s.cancel();
        repo.save(s);

    }

    public void addPayment(ServiceExecution s, Payment p) {

        s.addPayment(p);
        repo.save(s);

    }

    public void removePayment(ServiceExecution s, Payment p) {

        s.removePayment(p);
        repo.save(s);

    }

    public boolean existsNotPaid() {

        return repo.existsByPaymentStatus(ServicePaymentStatus.NOT_PAID);

    }

    public List<ServiceExecution> findTop10ByPetIdOrderByDateDesc(Long id) {

        return repo.findTop10ByPetIdOrderByDateDesc(id);

    }

}
