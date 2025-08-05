package br.com.unnamed.demo.domain.petCare.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Repository
public interface PetCareRepository extends JpaRepository<PetCare, Long> {

    @Query("SELECT p FROM PetCare p WHERE p.status = 'ACTIVE'")
    List<PetCare> findAllActive();

    @Query("SELECT p FROM PetCare p WHERE p.status = 'INACTIVE'")
    List<PetCare> findAllInactive();

    @Query("SELECT DISTINCT p FROM PetCare p " +
            "WHERE (:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', CAST(:description AS STRING), '%')))"
            +
            "AND (:status IS NULL OR p.status = :status)")
    Page<PetCare> searchWithOptionalFilters(String description, Status status, Pageable pageable);

}
