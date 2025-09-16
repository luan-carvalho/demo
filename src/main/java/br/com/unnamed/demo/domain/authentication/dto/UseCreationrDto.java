package br.com.unnamed.demo.domain.authentication.dto;

import java.util.List;

public record UseCreationrDto(String name, String password, List<Long> selectedRoles) {

}
