package br.com.unnamed.demo.domain.authentication.model;

import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {

    }

    public User(String name, String password, Role role) {
        this.name = name;
        this.status = Status.ACTIVE;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void deactivate() {

        this.status = Status.INACTIVE;

    }

    public void activate() {

        this.status = Status.ACTIVE;

    }

    public Status getStatus() {
        return status;
    }

    public boolean isActive() {

        return status == Status.ACTIVE;

    }

    public boolean isInactive() {

        return status == Status.INACTIVE;

    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getSimpleName() {

        return this.name.split(" ")[0];

    }

    public void updateInfo(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

}
