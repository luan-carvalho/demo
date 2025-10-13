package br.com.unnamed.demo.domain.authentication.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Role> findAllRoles() {

        return roleRepo.findAll();

    }

    public User findById(Long id) {

        return userRepo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));

    }

    public Role findRoleById(Long roleId) {

        return roleRepo.findById(roleId).orElseThrow(() -> new NoSuchElementException("Role not found"));

    }

    public Page<User> searchWithOptionalFilters(Pageable p, String name, Status status) {

        return userRepo.searchWithOptionalFilters(p, name, status);

    }

    public User createUser(String name, Long roleId) {

        Role role = findRoleById(roleId);
        String password = passwordEncoder.encode("12345678");
        User newUser = new User(name, password, role);
        return userRepo.save(newUser);

    }

    public void updateInfo(Long userId, String name, Long roleId) {

        User existingUser = findById(roleId);
        Role role = findRoleById(roleId);

        existingUser.updateInfo(name, role);
        userRepo.save(existingUser);

    }

    public void activateUser(Long id) {

        User user = userRepo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        user.activate();
        userRepo.save(user);

    }

    public void deactivateUser(Long id) {

        User user = userRepo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        user.deactivate();
        userRepo.save(user);

    }

    public void updatePassword(Long id, String newPassword) {

        User user = userRepo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encodedPassword);
        userRepo.save(user);

    }

}
