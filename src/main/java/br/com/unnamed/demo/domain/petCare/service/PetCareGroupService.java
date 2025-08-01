package br.com.unnamed.demo.domain.petCare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.petCare.repository.PetCareGroupRepository;

@Service
public class PetCareGroupService {

    private PetCareGroupRepository repo;

    public PetCareGroupService(PetCareGroupRepository repo) {
        this.repo = repo;
    }

    public List<PetCareGroup> findAllActive() {

        return repo.findAllActive();

    }

    public List<PetCareGroup> findAllInactive() {

        return repo.findAllInactive();

    }

    public PetCareGroup findById(Long id) {

        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet care group not found"));

    }

    public void save(PetCareGroup petCareGroup) {

        repo.save(petCareGroup);

    }

    public void deactivate(Long id) {

        PetCareGroup toBeDeactivated = findById(id);
        toBeDeactivated.deactivate();
        save(toBeDeactivated);

    }

    public void activate(Long id) {

        PetCareGroup toBeActivated = findById(id);
        toBeActivated.activate();
        save(toBeActivated);

    }

}
