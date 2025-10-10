package br.com.unnamed.demo.domain.serviceExecution.facade;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.petCare.service.PetCareService;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.serviceExecution.service.ServiceExecutionService;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.service.TutorService;

@Service
public class ServiceExecutionFacade {

    private final ServiceExecutionService service;
    private final PetCareService petCareService;
    private final TutorService tutorService;

    public ServiceExecutionFacade(ServiceExecutionService service, PetCareService petCareService,
            TutorService tutorService) {
        this.service = service;
        this.petCareService = petCareService;
        this.tutorService = tutorService;
    }

    public boolean existsNotPaid() {
        return service.existsNotPaid();
    }

    public Object findByStatusAndDate(ServiceStatus serviceStatus) {
        return service.findByStatusAndDate(serviceStatus);
    }

    public Page<ServiceExecution> searchWithOptionalFilters(String name, LocalDate date, ServiceStatus status,
            ServicePaymentStatus paymentStatus, Pageable pageable) {
        return service.searchWithOptionalFilters(name, date, status, paymentStatus, pageable);
    }

    public ServiceExecution findServiceExecutionById(Long serviceExecutionid) {

        return service.findById(serviceExecutionid);

    }

    public List<PetCareGroup> findAllPetCareGroups() {

        return petCareService.findAllGroups();

    }

    public Page<Tutor> searchClientshWithOptionalFilters(String search, Pageable pageable, Status active) {
        return tutorService.searchWithOptionalFilters(search, pageable, active);
    }

    public ServiceExecution createServiceExecution(Long tutorId, Long petId) {

        Tutor tutor = tutorService.findById(tutorId);
        Pet pet = tutorService.findByTutorAndPetId(tutorId, petId);
        List<PetCare> petCares = petCareService.findAllActive();
        ServiceExecution created = service.create(tutor, pet, petCares);

        return created;

    }

    public ServiceExecution createServiceExecutionWithNewPet(Long tutorId, String petName) {

        Tutor tutor = tutorService.findById(tutorId);
        Pet pet = tutorService.createPetAndSaveToTutor(tutorId, petName);
        List<PetCare> petCares = petCareService.findAllActive();
        ServiceExecution created = service.create(tutor, pet, petCares);

        return created;

    }

    public void updateClientWithNewPet(Long serviceExecutionId, Long tutorId, String petName) {

        Tutor tutor = tutorService.findById(tutorId);
        Pet pet = tutorService.createPetAndSaveToTutor(tutorId, petName);
        service.updateTutorAndPet(serviceExecutionId, tutor, pet);

    }

    public void updateClient(Long serviceExecutionId, Long tutorId, Long petId) {

        Tutor tutor = tutorService.findById(tutorId);
        Pet pet = tutorService.findByTutorAndPetId(tutorId, petId);
        service.updateTutorAndPet(serviceExecutionId, tutor, pet);

    }

    public void updateServiceExecution(Long serviceExecutionId, List<Long> selectedItems, String obs) {

        service.updateInfo(serviceExecutionId, selectedItems, obs);

    }

    public void cancelServiceExecution(Long serviceId) {
        service.cancel(serviceId);
    }

    public void startServiceExecution(Long serviceId) {
        service.start(serviceId);
    }

    public void markServiceExecutionAsDone(Long serviceId) {
        service.markAsDone(serviceId);
    }

    public List<ServiceExecution> findTop10ByPetIdOrderByDateDesc(Long petId) {
        return service.findTop10ByPetIdOrderByDateDesc(petId);
    }

}
