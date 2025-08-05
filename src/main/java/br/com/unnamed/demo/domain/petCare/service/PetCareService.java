package br.com.unnamed.demo.domain.petCare.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.petCare.repository.PetCareRepository;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Service
public class PetCareService {

    private PetCareRepository repo;

    public PetCareService(PetCareRepository repo) {
        this.repo = repo;
    }

    public List<PetCare> findAllActive() {

        return repo.findAll().stream().filter(pc -> pc.isActive()).toList();

    }

    public List<PetCare> findAllInactive() {

        return repo.findAll().stream().filter(pc -> pc.isInactive()).toList();

    }

    public PetCare findById(Long id) {

        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet care group not found"));

    }

    public void save(PetCare petCare) {

        repo.save(petCare);

    }

    public void deactivate(Long id) {

        PetCare toBeDeactivated = findById(id);
        toBeDeactivated.deactivate();
        save(toBeDeactivated);

    }

    public void activate(Long id) {

        PetCare toBeActivated = findById(id);
        toBeActivated.activate();
        save(toBeActivated);

    }

    public Page<PetCare> searchWithOptionalFilters(String description, Status status, Pageable pageable) {

        return repo.searchWithOptionalFilters(description, status, pageable);

    }

}
