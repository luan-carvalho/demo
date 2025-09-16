package br.com.unnamed.demo.domain.authentication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unnamed.demo.domain.authentication.model.User;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
                SELECT DISTINCT u
                FROM User u
                WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', CAST(:name AS STRING), '%')))
                AND (:status is NULL OR u.status = :status)
                ORDER BY u.name
            """)
    Page<User> searchWithOptionalFilters(Pageable pageable, String name, Status status);

}
