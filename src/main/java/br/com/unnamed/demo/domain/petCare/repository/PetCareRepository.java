package br.com.unnamed.demo.domain.petCare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unnamed.demo.domain.petCare.model.PetCare;

@Repository
public interface PetCareRepository extends JpaRepository<PetCare, Long> {

    @Query("SELECT p FROM PetCare p WHERE p.status = 'ACTIVE'")
    List<PetCare> findAllActive();

    @Query("SELECT p FROM PetCare p WHERE p.status = 'INACTIVE'")
    List<PetCare> findAllInactive();

}
