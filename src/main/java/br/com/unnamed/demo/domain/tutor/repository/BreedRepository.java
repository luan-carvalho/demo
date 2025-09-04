package br.com.unnamed.demo.domain.tutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unnamed.demo.domain.tutor.model.valueObjects.Breed;

public interface BreedRepository extends JpaRepository<Breed, Long> {

    @Query("SELECT b FROM Breed b WHERE b.specie.id = :specieId ORDER BY b.description ASC")
    List<Breed> findBySpecieId(@Param("specieId") Long specieId);
    
}
