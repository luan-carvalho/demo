package br.com.unnamed.demo.domain.authentication.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.authentication.model.Role;
import br.com.unnamed.demo.domain.authentication.model.User;
import br.com.unnamed.demo.domain.authentication.repository.RoleRepository;
import br.com.unnamed.demo.domain.authentication.repository.UserRepository;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public UserService(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public List<Role> findAllRoles() {

        return roleRepo.findAll();

    }

    public User findById(Long id) {

        return userRepo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));

    }

    public Page<User> searchWithOptionalFilters(Pageable p, String name, Status status) {

        return userRepo.searchWithOptionalFilters(p, name, status);

    }

}
