package br.com.unnamed.demo.domain.authentication.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.unnamed.demo.domain.authentication.dto.UserCreationDto;
import br.com.unnamed.demo.domain.authentication.dto.UserEditionDto;
import br.com.unnamed.demo.domain.authentication.model.CustomUserDetails;
import br.com.unnamed.demo.domain.authentication.service.UserService;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (status == null) {
            status = Status.ACTIVE;
        }

        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("users", userService.searchWithOptionalFilters(pageable, name, status));

        model.addAttribute("name", name);
        model.addAttribute("status", status);
        model.addAttribute("view", "user/user-list");
        model.addAttribute("activePage", "users");
        model.addAttribute("pageTitle", "Usuários");
        return "layout/base-layout";

    }

    @GetMapping("{id}")
    public String getUser(@PathVariable Long id, Model model) {

        UserEditionDto u = UserEditionDto.toDto(userService.findById(id));

        model.addAttribute("user", u);
        model.addAttribute("roles", userService.findAllRoles());

        model.addAttribute("view", "user/user");
        model.addAttribute("activePage", "users");
        model.addAttribute("pageTitle", "Usuário");
        return "layout/base-layout";

    }

    @GetMapping("new")
    public String getNewUserForm(Model model) {

        model.addAttribute("user", new UserCreationDto(null, null, null));
        model.addAttribute("roles", userService.findAllRoles());

        model.addAttribute("view", "user/user");
        model.addAttribute("activePage", "users");
        model.addAttribute("pageTitle", "Novo Usuário");
        return "layout/base-layout";

    }

    @PostMapping("new")
    public String postNewUser(UserCreationDto user) {

        userService.createUser(user);
        return "redirect:/user";

    }

    @PostMapping("update")
    public String postUpdateUser(UserEditionDto user) {

        System.out.println("ID: " + user.id());
        System.out.println("Name: " + user.name());
        System.out.println("Role ID: " + user.roleId());

        userService.updateInfo(user);
        return "redirect:/user";

    }

    @GetMapping("/{userId}/inactivate")
    public String deactivateUser(@PathVariable Long userId) {

        userService.deactivateUser(userId);
        return "redirect:/user";

    }

    @GetMapping("/{userId}/activate")
    public String activateUser(@PathVariable Long userId) {

        userService.activateUser(userId);
        return "redirect:/user";

    }

    @GetMapping("updatePassword")
    public String updatePassword(@AuthenticationPrincipal CustomUserDetails user, Model model) {

        model.addAttribute("view", "user/update-password");
        model.addAttribute("pageTitle", "Alterar Senha");
        return "layout/base-layout";

    }

    @PostMapping("updatePassword")
    public String updatePassword(@AuthenticationPrincipal CustomUserDetails user, String newPassword,
            String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }

        userService.updatePassword(Long.parseLong(user.getUsername()), newPassword);

        return "redirect:/";

    }

}
