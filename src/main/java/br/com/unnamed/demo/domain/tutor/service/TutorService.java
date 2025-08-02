package br.com.unnamed.demo.domain.tutor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.repository.TutorRepository;

@Service
public class TutorService {

    private TutorRepository TutorRepo;

    public TutorService(TutorRepository TutorRepo) {

        this.TutorRepo = TutorRepo;

    }

    public Tutor findById(long id) {

        return TutorRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Tutor not found"));

    }

    public Pet findByTutorAndPetId(Long tutorId, Long petId) {

        Tutor c = findById(tutorId);
        return c.getOwnedPet(petId);

    }

    public Tutor save(Tutor Tutor) {

        return TutorRepo.save(Tutor);

    }

    public void deleteById(Long id) {

        TutorRepo.deleteById(id);

    }

    public List<Tutor> findAllActive() {

        return TutorRepo.findAllActive();

    }
    public List<Tutor> findAllInactive() {

        return TutorRepo.findAllInactive();

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

}
