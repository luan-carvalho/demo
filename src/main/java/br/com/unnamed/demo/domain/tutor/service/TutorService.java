package br.com.unnamed.demo.domain.tutor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.tutor.dtos.TutorFormDto;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.TutorGroup;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.repository.PetRepository;
import br.com.unnamed.demo.domain.tutor.repository.TutorGroupRepository;
import br.com.unnamed.demo.domain.tutor.repository.TutorRepository;

@Service
public class TutorService {

    private final TutorRepository tutorRepo;
    private final PetRepository petRepo;
    private final TutorGroupRepository groupRepo;

    public TutorService(TutorRepository tutorRepo, PetRepository petRepo, TutorGroupRepository groupRepo) {
        this.tutorRepo = tutorRepo;
        this.petRepo = petRepo;
        this.groupRepo = groupRepo;
    }

    public List<Tutor> findAllActive() {

        return tutorRepo.findAllActive();

    }

    public Tutor findById(Long id) {

        return tutorRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Tutor not found"));

    }

    public Pet findByTutorAndPetId(Long tutorId, Long petId) {

        Tutor c = findById(tutorId);
        return c.getOwnedPet(petId);

    }

    public Tutor save(Tutor Tutor) {

        return tutorRepo.save(Tutor);

    }

    public Tutor save(TutorFormDto tutor) {

        return tutorRepo.save(new Tutor(tutor.name(), tutor.phone()));

    }

    public Pet save(Pet pet) {

        return petRepo.save(pet);

    }

    public void deleteById(Long id) {

        tutorRepo.deleteById(id);

    }

    public void deactivate(Long id) {

        Tutor c = findById(id);
        c.deactivate();
        save(c);

    }

    public void activate(Long id) {

        Tutor c = findById(id);
        c.activate();
        save(c);

    }

    public void deactivatePet(Long tutorId, Long petId) {

        Tutor c = findById(tutorId);
        c.deactivatePet(petId);
        save(c);

    }

    public void activatePet(Long tutorId, Long petId) {

        Tutor c = findById(tutorId);
        c.activatePet(petId);
        save(c);

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
        save(tutor);

    }

    public Pet createPetAndSaveToTutor(Long tutorId, String petName) {

        Tutor tutor = findById(tutorId);
        Pet pet = new Pet(null, petName, Status.ACTIVE);
        tutor.addPet(pet);
        pet = petRepo.save(pet);
        tutor = tutorRepo.save(tutor);
        return pet;

    }

    public void updatePetName(Long tutorId, Long petId, String petName) {

        Tutor tutor = findById(tutorId);
        tutor.getOwnedPet(petId).updateName(petName);
        save(tutor);

    }

}
