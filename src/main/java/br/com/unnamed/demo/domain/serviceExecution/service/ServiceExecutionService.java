package br.com.unnamed.demo.domain.serviceExecution.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
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

        // if (serviceExecution.getPaymentStatus() == PaymentStatus.PAID)
        // throw new IllegalArgumentException("Não é possível cancelar um serviço que já
        // foi pago");

        s.cancel();
        repo.save(s);

    }

    public List<LocalDate> findNotPaidFromPreviousDates() {

        return repo.findNotPaidFromPreviousDates();

    }

}
