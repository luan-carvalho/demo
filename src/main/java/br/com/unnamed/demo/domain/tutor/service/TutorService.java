package br.com.unnamed.demo.domain.tutor.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.repository.TutorRepository;

@Service
public class TutorService {

    private final TutorRepository tutorRepo;

    public TutorService(TutorRepository tutorRepo) {

        this.tutorRepo = tutorRepo;

    }

    public Tutor findById(long id) {

        return tutorRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Tutor not found"));

    }

    public Pet findByTutorAndPetId(Long tutorId, Long petId) {

        Tutor c = findById(tutorId);
        return c.getOwnedPet(petId);

    }

    public Tutor save(Tutor Tutor) {

        return tutorRepo.save(Tutor);

    }

    public void deleteById(Long id) {

        tutorRepo.deleteById(id);

    }

    public Page<Tutor> findAllActiveWithPage(Pageable pageable) {

        return tutorRepo.findAllActiveWithPage(pageable);

    }

    public Page<Tutor> findAllInactiveWithPage(Pageable pageable) {

        return tutorRepo.findAllInactiveWithPage(pageable);

    }

    public List<Tutor> findAllActive() {

        return tutorRepo.findAllActive();

    }

    public List<Tutor> findAllInactive() {

        return tutorRepo.findAllInactive();

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

    public Page<Tutor> searchByTutorOrPetName(String name, Pageable pageable) {

        return tutorRepo.findByTutorOrPetName(name, pageable);

    }

}
