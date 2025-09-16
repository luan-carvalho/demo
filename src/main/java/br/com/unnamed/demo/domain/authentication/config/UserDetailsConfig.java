package br.com.unnamed.demo.domain.authentication.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.authentication.model.CustomUserDetails;
import br.com.unnamed.demo.domain.authentication.model.User;
import br.com.unnamed.demo.domain.authentication.repository.UserRepository;

@Service
public class UserDetailsConfig implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsConfig(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) {

        User user = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return new CustomUserDetails(user);

    }
}
