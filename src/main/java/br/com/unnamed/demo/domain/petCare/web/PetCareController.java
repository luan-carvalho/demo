package br.com.unnamed.demo.domain.petCare.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.unnamed.demo.domain.petCare.dtos.PetCareDto;
import br.com.unnamed.demo.domain.petCare.mapper.PetCareGroupMapper;
import br.com.unnamed.demo.domain.petCare.mapper.PetCareMapper;
import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.petCare.service.PetCareGroupService;
import br.com.unnamed.demo.domain.petCare.service.PetCareService;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Controller
@RequestMapping("petCare")
public class PetCareController {

    private PetCareService petCareService;
    private PetCareGroupService petCareGroupService;

    public PetCareController(PetCareService petCareService, PetCareGroupService petCareGroupService) {
        this.petCareService = petCareService;
        this.petCareGroupService = petCareGroupService;
    }

    @GetMapping
    public String listPetCares(Model model,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        status = status == null ? Status.ACTIVE : status;

        Pageable pageable = PageRequest.of(page, size, Sort.by("description").ascending());

        model.addAttribute("petCarePage", petCareService.searchWithOptionalFilters(description, status, pageable));
        model.addAttribute("status", status);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("description", description);
        model.addAttribute("view", "petCare/petCare-list");
        model.addAttribute("activePage", "services");
        model.addAttribute("pageScript", "/js/script.js");
        model.addAttribute("pageTitle", "Serviços");
        return "layout/base-layout";

    }

    @GetMapping("/new")
    public String newPetCareForm(Model model) {

        model.addAttribute("petCareGroups", PetCareGroupMapper.toDtoList(petCareGroupService.findAllActive()));
        model.addAttribute("petCare", PetCareDto.empty());
        model.addAttribute("view", "petCare/petCare");
        model.addAttribute("activePage", "services");
        model.addAttribute("pageTitle", "Serviço | Novo");
        return "layout/base-layout";

    }

    @GetMapping("/{id}")
    public String getPetCare(@PathVariable Long id, Model model) {

        PetCare p = petCareService.findById(id);

        model.addAttribute("petCareGroups", PetCareGroupMapper.toDtoList(petCareGroupService.findAllActive()));
        model.addAttribute("petCare", p);
        model.addAttribute("view", "petCare/petCare");
        model.addAttribute("activePage", "services");
        model.addAttribute("pageTitle", "Serviço | " + p.getDescription());
        return "layout/base-layout";
    }

    @PostMapping
    public String savePetCare(PetCareDto petCare) {

        if (petCare.id() != null) {

            PetCare existingPetCare = petCareService.findById(petCare.id());
            existingPetCare.updateDescription(petCare.description());
            petCareService.save(existingPetCare);
            return "redirect:/petCare";

        }

        PetCare newPetCare = PetCareMapper.toEntity(petCare);
        newPetCare.activate();

        petCareService.save(newPetCare);

        return "redirect:/petCare";

    }

    @GetMapping("/{id}/inactivate")
    public String deactivatePetCare(@PathVariable Long id) {

        petCareService.deactivate(id);
        return "redirect:/petCare";

    }

    @GetMapping("/{id}/activate")
    public String activatePetCare(@PathVariable Long id) {

        petCareService.activate(id);
        return "redirect:/petCare";

    }

}
