package br.com.unnamed.demo.domain.petCare.service;

import java.math.BigDecimal;
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

        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet care not found"));

    }

    public PetCareGroup findGroupById(Long id) {

        return groupRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet care group not found"));

    }

    public void deactivate(Long id) {

        PetCare toBeDeactivated = findById(id);
        toBeDeactivated.deactivate();
        repo.save(toBeDeactivated);

    }

    public void activate(Long id) {

        PetCare toBeActivated = findById(id);
        toBeActivated.activate();
        repo.save(toBeActivated);

    }

    public Page<PetCare> searchWithOptionalFilters(String description, PetCareGroup group, Status status,
            Pageable pageable) {

        return repo.searchWithOptionalFilters(description, status, group, pageable);

    }

    public PetCare createPetCare(String description, Long groupId, BigDecimal price) {

        PetCareGroup group = null;

        if (groupId != null)
            group = findGroupById(groupId);

        PetCare petCare = new PetCare(description, price, group);
        return repo.save(petCare);

    }

    public void updatePetCareInfo(Long petCareId, String description, Long groupId, BigDecimal price) {

        PetCare petCare = findById(petCareId);
        petCare.updatePrice(price);
        petCare.updateDescription(description);
        if (groupId != null) {

            PetCareGroup group = findGroupById(groupId);
            petCare.updateGroup(group);
        }
        repo.save(petCare);

    }

}
