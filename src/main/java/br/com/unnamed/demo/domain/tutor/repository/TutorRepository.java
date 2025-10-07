package br.com.unnamed.demo.domain.tutor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

        @Query("""
                        SELECT DISTINCT t
                        FROM Tutor t
                        LEFT JOIN t.pets p
                        WHERE (:status IS NULL OR t.status = :status)
                        AND (
                                :searchTerm IS NULL OR
                                LOWER(t.name) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS string), '%'))
                                OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS string), '%')))
                        ORDER BY t.name
                        """)
        Page<Tutor> searchWithOptionalFilters(@Param("searchTerm") String searchTerm, @Param("status") Status status,
                        Pageable pageable);

        @Query("""
                        SELECT t
                        FROM Tutor t
                        WHERE t.status = 'ACTIVE'
                        ORDER BY t.name ASC
                                """)
        List<Tutor> findAllActive();

}
