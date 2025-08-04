package br.com.unnamed.demo.domain.tutor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unnamed.demo.domain.tutor.model.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    @Query("SELECT c FROM Tutor c WHERE c.status = 'ACTIVE'")
    List<Tutor> findAllActive();

    @Query("SELECT c FROM Tutor c WHERE c.status = 'INACTIVE'")
    List<Tutor> findAllInactive();

    @Query("SELECT c FROM Tutor c WHERE c.status = 'ACTIVE'")
    Page<Tutor> findAllActiveWithPage(Pageable pageable);

    @Query("SELECT c FROM Tutor c WHERE c.status = 'INACTIVE'")
    Page<Tutor> findAllInactiveWithPage(Pageable pageable);

    @Query("SELECT DISTINCT t FROM Tutor t LEFT JOIN t.pets p WHERE " +
            "LOWER(t.info.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")

    Page<Tutor> findByTutorOrPetName(@Param("searchTerm") String searchTerm, Pageable pageable);

}
