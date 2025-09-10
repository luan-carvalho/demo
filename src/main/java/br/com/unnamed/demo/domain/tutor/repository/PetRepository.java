package br.com.unnamed.demo.domain.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unnamed.demo.domain.tutor.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long>  {
    
}
