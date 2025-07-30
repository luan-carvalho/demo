package br.com.unnamed.demo.domain.tutor.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unnamed.demo.domain.tutor.model.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    @Query("SELECT c FROM Tutor c WHERE c.status = 'ACTIVE'")
    List<Tutor> findAllActive(Pageable p);    

    @Query("SELECT c FROM Tutor c WHERE c.status = 'INACTIVE'")
    List<Tutor> findAllInactive(Pageable p);

}
