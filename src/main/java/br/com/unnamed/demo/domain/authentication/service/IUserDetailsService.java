package br.com.unnamed.demo.domain.authentication.service;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.authentication.model.CustomUserDetails;
import br.com.unnamed.demo.domain.authentication.model.User;
import br.com.unnamed.demo.domain.authentication.repository.UserRepository;

@Service
public class IUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public IUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) {

        User user = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (user.isInactive()) {

            throw new DisabledException("This user doesn't have access to the system!");

        }

        return new CustomUserDetails(user);

    }
}
