package br.com.unnamed.demo.domain.serviceExecution.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.serviceExecution.repository.ServiceExecutionRepository;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;

@Service
public class ServiceExecutionService {

    private final ServiceExecutionRepository repo;

    public ServiceExecutionService(ServiceExecutionRepository repo) {
        this.repo = repo;
    }

    public ServiceExecution findById(Long id) {

        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Service execution not found"));

    }

    public Page<ServiceExecution> searchWithOptionalFilters(String name,
            LocalDate date, ServiceStatus status,
            ServicePaymentStatus paymentStatus, Pageable pageable) {

        return repo.findWithOptionalFilters(name, date, status, paymentStatus,
                pageable);

    }

    public List<ServiceExecution> findByStatusAndDate(ServiceStatus status) {

        return repo.findByStatusAndDate(status, LocalDate.now());

    }

    public void updateTutorAndPet(Long serviceExecutionId, Tutor tutor, Pet pet) {

        ServiceExecution serviceExecution = findById(serviceExecutionId);
        serviceExecution.updateTutorAndPet(tutor, pet);
        repo.save(serviceExecution);

    }

    public void start(Long serviceExecutionId) {

        ServiceExecution s = findById(serviceExecutionId);
        s.start();
        repo.save(s);

    }

    public void markAsDone(Long serviceExecutionId) {

        ServiceExecution s = findById(serviceExecutionId);
        s.markAsDone();
        repo.save(s);

    }

    public boolean isServiceExecutionEmpty(Long serviceExecutionId) {

        ServiceExecution s = findById(serviceExecutionId);
        return s.isEmpty();

    }

    public void cancel(Long serviceExecutionId) {

        ServiceExecution s = findById(serviceExecutionId);

        if (s.isEmpty()) {

            repo.delete(s);
            return;

        }

        s.cancel();
        repo.save(s);

    }

    public boolean existsNotPaid() {

        return !repo.existsWithPendingPayment().isEmpty();

    }

    public List<ServiceExecution> findTop10ByPetIdOrderByDateDesc(Long id) {

        return repo.findTop10ByPetIdOrderByDateDesc(id);

    }

    public void addPayment(ServiceExecution serviceExecution, Payment payment) {

        serviceExecution.addPayment(payment);
        repo.save(serviceExecution);

    }

    public void addPayments(ServiceExecution serviceExecution, List<Payment> payments) {

        serviceExecution.addPayments(payments);
        repo.save(serviceExecution);

    }

    public void removePayment(Long serviceExecutionId, Payment payment) {

        ServiceExecution serviceExecution = findById(serviceExecutionId);
        serviceExecution.removePayment(payment);
        repo.save(serviceExecution);

    }

    public void finish(Long serviceId) {

        ServiceExecution service = findById(serviceId);
        service.finish();
        repo.save(service);

    }

    public ServiceExecution create(Tutor tutor, Pet pet, List<PetCare> petCares) {

        return repo.save(new ServiceExecution(tutor, pet, petCares));

    }

    public void updateInfo(Long serviceExecutionId, List<Long> selectedItems,
            String obs) {

        ServiceExecution toBeUpdated = findById(serviceExecutionId);
        toBeUpdated.updateChecklist(selectedItems);
        toBeUpdated.updateObservation(obs);
        repo.save(toBeUpdated);

    }

    public void reopenServiceExecution(Long serviceId) {

        ServiceExecution s = findById(serviceId);
        s.returnCompletedToDone();
        repo.save(s);

    }

}
