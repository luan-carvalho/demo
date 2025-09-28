package br.com.unnamed.demo.domain.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unnamed.demo.domain.tutor.model.TutorGroup;

public interface TutorGroupRepository extends JpaRepository<TutorGroup, Long> {

}
