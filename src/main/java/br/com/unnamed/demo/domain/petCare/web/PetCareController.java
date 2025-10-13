package br.com.unnamed.demo.domain.petCare.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.unnamed.demo.domain.petCare.dto.PetCareFormDto;
import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.petCare.service.PetCareService;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Controller
@RequestMapping("petCare")
public class PetCareController {

    private PetCareService petCareService;

    public PetCareController(PetCareService petCareService) {
        this.petCareService = petCareService;
    }

    @GetMapping
    public String listPetCares(Model model,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) PetCareGroup petCareGroup,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        status = status == null ? Status.ACTIVE : status;

        Pageable pageable = PageRequest.of(page, size);

        model.addAttribute("petCarePage",
                petCareService.searchWithOptionalFilters(description, petCareGroup, status, pageable));
        model.addAttribute("status", status);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("description", description);
        model.addAttribute("view", "petCare/petCare-list");
        model.addAttribute("activePage", "services");
        model.addAttribute("pageTitle", "Serviços");
        return "layout/base-layout";

    }

    @GetMapping("/new")
    public String newPetCareForm(Model model) {

        model.addAttribute("petCareGroups", petCareService.findAllGroups());
        model.addAttribute("petCare", PetCareFormDto.empty());
        model.addAttribute("view", "petCare/petCare");
        model.addAttribute("activePage", "services");
        model.addAttribute("pageTitle", "Serviço | Novo");
        return "layout/base-layout";

    }

    @GetMapping("/{id}")
    public String getPetCare(@PathVariable Long id, Model model) {

        model.addAttribute("petCareGroups", petCareService.findAllGroups());
        model.addAttribute("petCare", new PetCareFormDto(petCareService.findById(id)));
        model.addAttribute("view", "petCare/petCare");
        model.addAttribute("activePage", "services");
        model.addAttribute("pageTitle", "Serviço");
        return "layout/base-layout";
    }

    @PostMapping
    public String createPetCare(PetCareFormDto petCare, RedirectAttributes attributes) {

        PetCare created = petCareService.createPetCare(petCare.description(), petCare.price(), petCare.groupId());
        attributes.addFlashAttribute("successMessage", "Serviço cadastrado!");
        return "redirect:/petCare/" + created.getId();

    }

    @PostMapping("{petCareId}")
    public String updatePetCare(@PathVariable Long petCareId, PetCareFormDto petCare, RedirectAttributes attributes) {

        petCareService.updatePetCare(petCare.id(), petCare.description(), petCare.price(), petCare.groupId());
        attributes.addFlashAttribute("successMessage", "Serviço atualizado!");
        return "redirect:/petCare";

    }

    @PostMapping("/{petCareId}/inactivate")
    public String deactivatePetCare(@PathVariable Long petCareId, RedirectAttributes attributes) {

        petCareService.deactivate(petCareId);
        attributes.addFlashAttribute("successMessage", "Serviço inativado com sucesso!");
        return "redirect:/petCare/" + petCareId;

    }

    @PostMapping("/{petCareId}/activate")
    public String activatePetCare(@PathVariable Long petCareId, RedirectAttributes attributes) {

        petCareService.activate(petCareId);
        attributes.addFlashAttribute("successMessage", "Serviço ativado com sucesso!");
        return "redirect:/petCare/" + petCareId;

    }

}
