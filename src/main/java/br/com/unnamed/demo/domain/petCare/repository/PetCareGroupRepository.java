package br.com.unnamed.demo.domain.petCare.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Repository
public interface PetCareGroupRepository extends JpaRepository<PetCareGroup, Long> {

    @Query("SELECT p FROM PetCareGroup p WHERE p.status = 'ACTIVE'")
    List<PetCareGroup> findAllActive();

    @Query("SELECT p FROM PetCareGroup p WHERE p.status = 'INACTIVE'")
    List<PetCareGroup> findAllInactive();

    @Query("SELECT DISTINCT p FROM PetCareGroup p " +
            "WHERE (:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', CAST(:description AS STRING), '%')))" +
            "AND (:status IS NULL OR p.status = :status)")
    Page<PetCareGroup> searchWithOptionalFilters(String description, Status status, Pageable pageable);

}
