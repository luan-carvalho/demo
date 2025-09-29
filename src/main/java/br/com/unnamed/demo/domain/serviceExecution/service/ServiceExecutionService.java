package br.com.unnamed.demo.domain.serviceExecution.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.enums.PaymentStatus;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
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

    public ServiceExecution save(ServiceExecution serviceExecution) {

        return repo.save(serviceExecution);

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

        s.markAsDone();
        repo.save(s);

    }

    public void cancel(ServiceExecution s) {

        s.cancel();
        repo.save(s);

    }

    public boolean existsNotPaid() {

        return !repo.existsWithPendingPayment().isEmpty();

    }

    public List<ServiceExecution> findTop10ByPetIdOrderByDateDesc(Long id) {

        return repo.findTop10ByPetIdOrderByDateDesc(id);

    }

    public void addPayment(ServiceExecution s, PaymentMethod method, BigDecimal amount, String obs) {

        if (amount.compareTo(s.calculateTotal()) > 0) {

            amount = s.calculateTotal();

        }

        Payment p = new Payment(LocalDate.now(), method, amount, PaymentStatus.TEMPORARY, obs);

        s.addPayment(p);
        repo.save(s);

    }

    public void removePayment(ServiceExecution s, Payment p) {

        if (s.getServiceStatus() != ServiceStatus.COMPLETED) {

            s.removePayment(p);
            p.removeLinkToServiceExecution();

        }

        if (s.getServiceStatus() == ServiceStatus.COMPLETED) {

            p.updateStatus(PaymentStatus.CANCELLED);

        }

        repo.save(s);

    }

    public void finish(Long serviceId) {

        ServiceExecution service = findById(serviceId);
        service.markAsPaid();
        repo.save(service);

    }

}
