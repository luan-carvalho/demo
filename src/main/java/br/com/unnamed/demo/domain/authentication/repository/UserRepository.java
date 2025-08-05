package br.com.unnamed.demo.domain.authentication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unnamed.demo.domain.authentication.model.UserImpl;

public interface UserRepository extends JpaRepository<UserImpl, Long> {

    @Query("SELECT u FROM UserImpl u WHERE u.email.email = :username AND u.status = 'ACTIVE'")
    Optional<UserImpl> findByUsername(String username);

    @Query("SELECT u FROM UserImpl u WHERE u.status = 'ACTIVE'")
    List<UserImpl> findAllActive();

    @Query("SELECT u FROM UserImpl u WHERE u.status = 'INACTIVE'")
    List<UserImpl> findAllInactive();

}
