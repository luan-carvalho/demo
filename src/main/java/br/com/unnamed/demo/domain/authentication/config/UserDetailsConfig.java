package br.com.unnamed.demo.domain.authentication.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.authentication.model.UserImpl;
import br.com.unnamed.demo.domain.authentication.repository.UserRepository;

@Service
public class UserDetailsConfig implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsConfig(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {

        UserImpl user = repository.findByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return new User(user.getUsername(), user.getPassword(), true, true, true,
                true, user.getAuthorities());

    }
}
