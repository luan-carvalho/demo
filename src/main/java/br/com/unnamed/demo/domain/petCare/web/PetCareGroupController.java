package br.com.unnamed.demo.domain.petCare.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.unnamed.demo.domain.petCare.dtos.PetCareGroupDto;
import br.com.unnamed.demo.domain.petCare.mapper.PetCareGroupMapper;
import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.petCare.service.PetCareGroupService;

@Controller
@RequestMapping("/petCareGroup")
public class PetCareGroupController {

    private PetCareGroupService petCareGroupService;

    public PetCareGroupController(PetCareGroupService petCareGroupService) {

        this.petCareGroupService = petCareGroupService;

    }

    @GetMapping
    public String getAllpetCareGroups(Model model) {

        model.addAttribute("petCareGroups", PetCareGroupMapper.toDtoList(petCareGroupService.findAllActive()));

        model.addAttribute("view", "petCareGroup/petCareGroup-list");
        model.addAttribute("activePage", "service-groups");
        model.addAttribute("pageScript", "/js/script.js");
        return "layout/base-layout";

    }

    @GetMapping("/new")
    public String newPetCareGroupForm(Model model) {

        model.addAttribute("petCareGroup", PetCareGroupDto.empty());
        model.addAttribute("view", "petCareGroup/petCareGroup");
        model.addAttribute("activePage", "service-groups");
        return "layout/base-layout";

    }

    @GetMapping("/{id}")
    public String getPetCareGroup(@PathVariable Long id, Model model) {

        model.addAttribute("petCareGroup", petCareGroupService.findById(id));
        model.addAttribute("view", "petCareGroup/petCareGroup");
        model.addAttribute("activePage", "service-groups");
        return "layout/base-layout";
    }

    @PostMapping
    public String savePetCareGroup(PetCareGroupDto petCareGroup) {

        if (petCareGroup.id() != null) {

            PetCareGroup existingPetCareGroup = petCareGroupService.findById(petCareGroup.id());
            existingPetCareGroup.updateDescription(petCareGroup.description());
            petCareGroupService.save(existingPetCareGroup);
            return "redirect:/petCareGroup";

        }

        PetCareGroup p = PetCareGroupMapper.toEntity(petCareGroup);
        p.activate();

        petCareGroupService.save(p);

        return "redirect:/petCareGroup";

    }

    @GetMapping("/{id}/inactivate")
    public String deactivatePetCareGroup(@PathVariable Long id) {

        petCareGroupService.deactivate(id);
        return "redirect:/petCareGroup";

    }

    @GetMapping("/{id}/activate")
    public String activatePetCareGroup(@PathVariable Long id) {

        petCareGroupService.activate(id);
        return "redirect:/petCareGroup";

    }

    @GetMapping("/search")
    public String searchByDescription(@RequestParam(required = false) String description, Model model) {

        if (description == null || description.isBlank()) {

            return "redirect:/petCareGroup";

        }

        model.addAttribute("petCareGroups",
                PetCareGroupMapper.toDtoList(petCareGroupService.searchByDescription(description)));
        model.addAttribute("description", description);
        model.addAttribute("view", "petCareGroup/petCareGroup-list");
        model.addAttribute("activePage", "service-groups");
        model.addAttribute("pageScript", "/js/script.js");
        return "layout/base-layout";

    }

}
