package br.com.unnamed.demo.domain.authentication.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User u) {

        this.user = u;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.user.getRole());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getId().toString();
    }

    public String getName() {

        return this.user.getSimpleName();

    }

}
