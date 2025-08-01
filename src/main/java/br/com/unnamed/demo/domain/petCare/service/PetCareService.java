package br.com.unnamed.demo.domain.petCare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.petCare.repository.PetCareRepository;

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

}
