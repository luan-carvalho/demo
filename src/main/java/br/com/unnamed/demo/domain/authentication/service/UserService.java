package br.com.unnamed.demo.domain.authentication.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.authentication.model.UserImpl;
import br.com.unnamed.demo.domain.authentication.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserImpl findById(Long id) {

        return userRepo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));

    }

    public List<UserImpl> findAllActiveUsers() {

        return userRepo.findAllActive();

    }

    public List<UserImpl> findAllInactiveActiveUsers() {

        return userRepo.findAllInactive();

    }

}
