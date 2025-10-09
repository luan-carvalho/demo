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
        model.addAttribute("petCare", new PetCare());
        model.addAttribute("view", "petCare/petCare");
        model.addAttribute("activePage", "services");
        model.addAttribute("pageTitle", "Serviço | Novo");
        return "layout/base-layout";

    }

    @GetMapping("/{id}")
    public String getPetCare(@PathVariable Long id, Model model) {

        PetCare p = petCareService.findById(id);

        model.addAttribute("petCareGroups", petCareService.findAllGroups());
        model.addAttribute("petCare", p);
        model.addAttribute("view", "petCare/petCare");
        model.addAttribute("activePage", "services");
        model.addAttribute("pageTitle", "Serviço | " + p.getDescription());
        return "layout/base-layout";
    }

    @PostMapping
    public String createPetCare(PetCare petCare, RedirectAttributes attributes) {

        return "redirect:/petCare";

    }

    @PostMapping("{petCareId}")
    public String updatePetCare(@PathVariable Long petCareId, PetCare petCare, RedirectAttributes attributes) {

        return "redirect:/petCare";

    }

    @GetMapping("/{petCareId}/inactivate")
    public String deactivatePetCare(@PathVariable Long petCareId) {

        petCareService.deactivate(petCareId);
        return "redirect:/petCare";

    }

    @GetMapping("/{petCareId}/activate")
    public String activatePetCare(@PathVariable Long petCareId) {

        petCareService.activate(petCareId);
        return "redirect:/petCare";

    }

}
