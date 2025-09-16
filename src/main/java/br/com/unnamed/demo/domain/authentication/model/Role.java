package br.com.unnamed.demo.domain.authentication.model;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    private String description;

    @Override
    public String getAuthority() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLabel() {
        return label;
    }

}
