package br.com.unnamed.demo.domain.tutor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.tutor.exception.TutorGroupNotFoundException;
import br.com.unnamed.demo.domain.tutor.exception.TutorNotFoundException;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.TutorGroup;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.repository.TutorGroupRepository;
import br.com.unnamed.demo.domain.tutor.repository.TutorRepository;

@Service
public class TutorService {

    private final TutorRepository tutorRepo;
    private final TutorGroupRepository groupRepo;

    public TutorService(TutorRepository tutorRepo, TutorGroupRepository groupRepo) {
        this.tutorRepo = tutorRepo;
        this.groupRepo = groupRepo;
    }

    public List<Tutor> findAllActive() {

        return tutorRepo.findAllActive();

    }

    public Tutor findById(Long id) {

        return tutorRepo.findById(id).orElseThrow(() -> new TutorNotFoundException("Tutor not found"));

    }

    public Pet findByTutorAndPetId(Long tutorId, Long petId) {

        Tutor tutor = findById(tutorId);
        return tutor.getOwnedPet(petId);

    }

    public Tutor createTutor(String name, String phone, Long groupId) {

        TutorGroup group = null;

        if (groupId != null)
            group = findGroupById(groupId);

        return tutorRepo.save(new Tutor(name, phone, group));

    }

    public Tutor updateTutorInfo(Long tutorId, String name, String phone, Long groupId) {

        Tutor toBeUpdated = findById(tutorId);
        TutorGroup group = null;

        if (groupId != null)
            group = findGroupById(groupId);

        toBeUpdated.updateTutorInfo(phone, name);
        toBeUpdated.updateTutorGroup(group);

        return tutorRepo.save(toBeUpdated);

    }

    public void deleteById(Long id) {

        tutorRepo.deleteById(id);

    }

    public void deactivate(Long id) {

        Tutor tutor = findById(id);
        tutor.deactivate();
        tutorRepo.save(tutor);

    }

    public void activate(Long id) {

        Tutor tutor = findById(id);
        tutor.activate();
        tutorRepo.save(tutor);

    }

    public void deactivatePet(Long tutorId, Long petId) {

        Tutor tutor = findById(tutorId);
        tutor.deactivatePet(petId);
        tutorRepo.save(tutor);

    }

    public void activatePet(Long tutorId, Long petId) {

        Tutor tutor = findById(tutorId);
        tutor.activatePet(petId);
        tutorRepo.save(tutor);

    }

    public Page<Tutor> searchWithOptionalFilters(String name, Pageable pageable, Status status) {

        return tutorRepo.searchWithOptionalFilters(name, status, pageable);

    }

    public List<TutorGroup> findAllGroups() {

        return groupRepo.findAll();

    }

    public void createAndSetGroup(String description, Long tutorId) {

        Tutor tutor = findById(tutorId);
        TutorGroup createdGroup = groupRepo.save(new TutorGroup(null, description));
        tutor.updateTutorGroup(createdGroup);
        tutorRepo.save(tutor);

    }

    public Pet createPetAndSaveToTutor(Long tutorId, String petName) {

        Tutor tutor = findById(tutorId);
        Pet pet = new Pet(petName);
        tutor.addPet(pet);
        tutor = tutorRepo.save(tutor);
        return tutor.getPets()
                .stream()
                .filter(p -> p.getName().equals(petName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("`Pet was not saved properly"));

    }

    public void updatePetName(Long tutorId, Long petId, String petName) {

        Tutor tutor = findById(tutorId);
        tutor.getOwnedPet(petId).updateName(petName);
        tutorRepo.save(tutor);

    }

    public TutorGroup findGroupById(Long groupId) {

        return groupRepo.findById(groupId)
                .orElseThrow(() -> new TutorGroupNotFoundException("Group not found"));

    }

}
