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

import br.com.unnamed.demo.domain.petCare.dtos.PetCareGroupDto;
import br.com.unnamed.demo.domain.petCare.mapper.PetCareGroupMapper;
import br.com.unnamed.demo.domain.petCare.model.PetCareGroup;
import br.com.unnamed.demo.domain.petCare.service.PetCareGroupService;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Controller
@RequestMapping("/petCareGroup")
public class PetCareGroupController {

    private PetCareGroupService petCareGroupService;

    public PetCareGroupController(PetCareGroupService petCareGroupService) {

        this.petCareGroupService = petCareGroupService;

    }

    @GetMapping
    public String listPetCareGroups(Model model,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        status = status == null ? Status.ACTIVE : status;

        Pageable pageable = PageRequest.of(page, size, Sort.by("description").ascending());

        model.addAttribute("groupPage", petCareGroupService.searchWithOptionalFilters(description, status, pageable));
        model.addAttribute("status", status);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("description", description);
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

}
