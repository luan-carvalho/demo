package br.com.unnamed.demo.domain.petCarePackage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unnamed.demo.domain.petCarePackage.model.PetCarePackage;

public interface PetCarePackageRepository extends JpaRepository<PetCarePackage, Long> {

}
