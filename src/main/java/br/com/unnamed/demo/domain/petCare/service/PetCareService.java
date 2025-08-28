package br.com.unnamed.demo.domain.petCare.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.petCare.repository.PetCareGroupRepository;
import br.com.unnamed.demo.domain.petCare.repository.PetCareRepository;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Service
public class PetCareService {

    private final PetCareGroupRepository groupRepo;
    private final PetCareRepository repo;

    public PetCareService(PetCareGroupRepository groupRepo, PetCareRepository repo) {
        this.groupRepo = groupRepo;
        this.repo = repo;
    }

    public List<PetCare> findAllActive() {

        return repo.findAllActive();

    }

    public List<PetCareGroup> findAllGroups() {

        return groupRepo.findAll();

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

    public Page<PetCare> searchWithOptionalFilters(String description, PetCareGroup group, Status status,
            Pageable pageable) {

        return repo.searchWithOptionalFilters(description, status, group, pageable);

    }

}
