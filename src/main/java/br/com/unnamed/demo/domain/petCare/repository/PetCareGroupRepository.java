package br.com.unnamed.demo.domain.petCare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;

@Repository
public interface PetCareGroupRepository extends JpaRepository<PetCareGroup, Long> {

    @Query("SELECT p FROM PetCareGroup p WHERE p.status = 'ACTIVE'")
    List<PetCareGroup> findAllActive();

    @Query("SELECT p FROM PetCareGroup p WHERE p.status = 'INACTIVE'")
    List<PetCareGroup> findAllInactive();

}
