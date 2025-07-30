package br.com.unnamed.demo.domain.petCare.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "petCareGroup/petCareGroup-list";

    }

    @GetMapping("/new")
    public String newPetCareGroupForm(Model model) {

        model.addAttribute("petCareGroup", PetCareGroupDto.empty());
        return "petCareGroup/petCareGroup";

    }

    @GetMapping("/{id}")
    public String getPetCareGroup(@PathVariable Long id, Model model) {

        model.addAttribute("petCareGroup", petCareGroupService.findById(id));
        return "petCareGroup/petCareGroup";
    }

    @PostMapping
    public String savePetCareGroup(PetCareGroupDto petCareGroup) {

        if (petCareGroup.id() != null) {

            PetCareGroup existingPetCareGroup = petCareGroupService.findById(petCareGroup.id());
            existingPetCareGroup.updateDescription(petCareGroup.description());
            petCareGroupService.save(existingPetCareGroup);
            return "redirect:/petCareGroup";

        }

        petCareGroupService.save(PetCareGroupMapper.toEntity(petCareGroup));

        return "redirect:/petCareGroup";

    }

}
