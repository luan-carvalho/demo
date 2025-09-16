package br.com.unnamed.demo.domain.authentication.dto;

import br.com.unnamed.demo.domain.authentication.model.Role;
import br.com.unnamed.demo.domain.authentication.model.User;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public record UserEditionDto(Long id, String name, Status status, Role role) {

    public static UserEditionDto toDto(User u) {

        return new UserEditionDto(u.getId(), u.getName(), u.getStatus(), u.getRole());

    }

}
