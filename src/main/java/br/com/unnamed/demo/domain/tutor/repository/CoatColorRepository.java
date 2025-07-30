package br.com.unnamed.demo.domain.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unnamed.demo.domain.tutor.model.valueObjects.CoatColor;

public interface CoatColorRepository extends JpaRepository<CoatColor, Long> {
    
}
