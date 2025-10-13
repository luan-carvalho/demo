package br.com.unnamed.demo.domain.authentication.dto;

import br.com.unnamed.demo.domain.authentication.model.User;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public record UserFormDto(Long id, String name, Status status, Long roleId) {

    public UserFormDto(User u) {

        this(u.getId(), u.getName(), u.getStatus(), u.getRole().getId());

    }

    public static UserFormDto empty() {

        return new UserFormDto(null, null, null, null);

    }

}
