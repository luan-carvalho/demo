package br.com.unnamed.demo.domain.tutor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.tutor.model.valueObjects.Breed;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.CoatColor;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Specie;
import br.com.unnamed.demo.domain.tutor.repository.BreedRepository;
import br.com.unnamed.demo.domain.tutor.repository.CoatColorRepository;
import br.com.unnamed.demo.domain.tutor.repository.SpecieRepository;

@Service
public class PetInfoService {

    private final BreedRepository breedRepo;
    private final CoatColorRepository coatColorRepo;
    private final SpecieRepository specieRepository;

    public PetInfoService(BreedRepository breedRepo, CoatColorRepository coatColorRepo,
            SpecieRepository specieRepository) {

        this.breedRepo = breedRepo;
        this.coatColorRepo = coatColorRepo;
        this.specieRepository = specieRepository;

    }

    public List<Specie> findAllSpecies() {

        return specieRepository.findAll();

    }

    public List<CoatColor> findAllCoatColors() {

        return coatColorRepo.findAll();

    }

    public List<Breed> findAllBreedsFromSpecieId(Long specieId) {

        return breedRepo.findBySpecieId(specieId);

    }

}
