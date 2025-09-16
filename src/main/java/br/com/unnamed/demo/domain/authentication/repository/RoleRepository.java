package br.com.unnamed.demo.domain.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unnamed.demo.domain.authentication.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
