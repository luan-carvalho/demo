package br.com.unnamed.demo.domain.petCare.web;

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
    public String getAllActivePetCares(Model model) {

        model.addAttribute("petCares", PetCareMapper.toDtoList(petCareService.findAllActive()));
        model.addAttribute("view", "petCare/petCare-list");
        model.addAttribute("activePage", "services");
        model.addAttribute("pageScript", "/js/script.js");
        return "layout/base-layout";

    }

    @GetMapping("/new")
    public String newPetCareForm(Model model) {

        model.addAttribute("petCareGroups", PetCareGroupMapper.toDtoList(petCareGroupService.findAllActive()));
        model.addAttribute("petCare", PetCareDto.empty());
        model.addAttribute("view", "petCare/petCare");
        model.addAttribute("activePage", "services");
        return "layout/base-layout";

    }

    @GetMapping("/{id}")
    public String getPetCare(@PathVariable Long id, Model model) {

        model.addAttribute("petCareGroups", PetCareGroupMapper.toDtoList(petCareGroupService.findAllActive()));
        model.addAttribute("petCare", petCareService.findById(id));
        model.addAttribute("view", "petCare/petCare");
        model.addAttribute("activePage", "services");
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

    @GetMapping("/search")
    public String searchByDescription(@RequestParam(required = false) String description, Model model) {

        if (description == null || description.isBlank()) {

            return "redirect:/petCare";

        }

        model.addAttribute("petCares",
                PetCareMapper.toDtoList(petCareService.searchByDescription(description)));
        model.addAttribute("description", description);
        model.addAttribute("view", "petCare/petCare-list");
        model.addAttribute("activePage", "services");
        model.addAttribute("pageScript", "/js/script.js");
        return "layout/base-layout";

    }

}
