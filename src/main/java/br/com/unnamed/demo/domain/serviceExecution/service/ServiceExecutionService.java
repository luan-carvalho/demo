package br.com.unnamed.demo.domain.serviceExecution.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecutionItem;
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

    public ServiceExecution createServiceExecution(Tutor tutor, Pet pet, List<PetCare> petCares) {

        ServiceExecution newServiceExecution = new ServiceExecution(tutor, pet);
        petCares.forEach(p -> newServiceExecution.addService(p));
        repo.save(newServiceExecution);
        return newServiceExecution;

    }

    public void addService(ServiceExecution serviceExecution, PetCare petCare, int quantity) {

        serviceExecution.addService(petCare);
        repo.save(serviceExecution);

    }

    public void removeService(ServiceExecution serviceExecution, ServiceExecutionItem item) {

        serviceExecution.removeService(item);
        repo.save(serviceExecution);

    }

    public void startServiceExecution(ServiceExecution serviceExecution) {

        serviceExecution.startService();
        repo.save(serviceExecution);

    }

    public void finishServiceExecution(ServiceExecution serviceExecution) {

        serviceExecution.finishService();
        repo.save(serviceExecution);

    }

    public List<ServiceExecution> findAllPending(LocalDate date) {

        return repo.findAllByStatusAndDate(ServiceStatus.PENDING, date);

    }

    public List<ServiceExecution> findAllInProgress(LocalDate date) {

        return repo.findAllByStatusAndDate(ServiceStatus.PENDING, date);

    }

    public List<ServiceExecution> findAllCompleted(LocalDate date) {

        return repo.findAllByStatusAndDate(ServiceStatus.PENDING, date);

    }

}
