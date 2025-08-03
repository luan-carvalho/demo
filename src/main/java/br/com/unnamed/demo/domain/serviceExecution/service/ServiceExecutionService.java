package br.com.unnamed.demo.domain.serviceExecution.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecutionItem;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.PaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.serviceExecution.repository.ServiceExecutionRepository;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;

@Service
public class ServiceExecutionService {

    private ServiceExecutionRepository repo;

    public ServiceExecutionService(ServiceExecutionRepository repo) {
        this.repo = repo;
    }

    public ServiceExecution findById(Long id) {

        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Service execution not found"));

    }

    public void create(Tutor tutor, Pet pet, List<PetCare> petCares) {

        ServiceExecution service = new ServiceExecution(tutor, pet);
        service.addServices(petCares);
        repo.save(service);

    }

    public void update(ServiceExecution serviceExecution, List<PetCare> petCares) {

        serviceExecution.updatePetCares(petCares);
        repo.save(serviceExecution);

    }

    public void addService(ServiceExecution serviceExecution, PetCare petCare, int quantity) {

        serviceExecution.addService(petCare);
        repo.save(serviceExecution);

    }

    public void removeService(ServiceExecution serviceExecution, ServiceExecutionItem item) {

        serviceExecution.removeService(item);
        repo.save(serviceExecution);

    }

    public List<ServiceExecution> findByStatus(ServiceStatus status) {

        return repo.findAllByStatusAndDate(status, LocalDate.now());

    }

    public void start(Long serviceId) {

        ServiceExecution s = findById(serviceId);
        s.startService();
        repo.save(s);

    }

    public void finish(Long serviceId) {

        ServiceExecution s = findById(serviceId);
        s.finishService();
        repo.save(s);

    }

    public void cancel(Long serviceId) {

        ServiceExecution serviceExecution = findById(serviceId);

        if (serviceExecution.getPaymentStatus() == PaymentStatus.PAID)
            throw new IllegalArgumentException("Não é possível cancelar um serviço que já foi pago");

        repo.delete(serviceExecution);

    }

}
