package br.com.unnamed.demo.domain.tutor.web;

import org.springframework.data.domain.Page;
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

import br.com.unnamed.demo.domain.tutor.dtos.PetFormDto;
import br.com.unnamed.demo.domain.tutor.dtos.TutorFormDto;
import br.com.unnamed.demo.domain.tutor.mapper.PetMapper;
import br.com.unnamed.demo.domain.tutor.mapper.TutorMapper;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.enums.Gender;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Phone;
import br.com.unnamed.demo.domain.tutor.service.PetInfoService;
import br.com.unnamed.demo.domain.tutor.service.TutorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("tutor")
public class TutorController {

    private final TutorService tutorService;
    private final PetInfoService petInfoService;

    public TutorController(TutorService tutorService, PetInfoService petInfoService) {

        this.tutorService = tutorService;
        this.petInfoService = petInfoService;

    }

    @GetMapping
    public String listTutors(Model model, @RequestParam(required = false) String name,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        status = status == null ? Status.ACTIVE : status;

        Pageable pageable = PageRequest.of(page, size, Sort.by("info.name").ascending());
        Page<Tutor> tutorPage = tutorService.searchWithOptionalFilters(name, pageable, status);

        model.addAttribute("tutorPage", tutorPage);
        model.addAttribute("name", name);
        model.addAttribute("status", status);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("view", "tutor/tutor-list");
        model.addAttribute("activePage", "clients");
        model.addAttribute("pageTitle", "Clientes");
        model.addAttribute("pageScript", "/js/script.js");
        return "layout/base-layout";
        
    }
    
    @GetMapping("/{id}")
    public String findTutorById(@PathVariable Long id, Model model) {
        
        TutorFormDto tutorDto = TutorMapper.toForm(tutorService.findById(id));
        model.addAttribute("tutor", tutorDto);
        model.addAttribute("activePage", "clients");
        model.addAttribute("view", "tutor/tutor");
        model.addAttribute("pageScript", "/js/script.js");
        model.addAttribute("pageTitle", "Clientes");
        return "layout/base-layout";
        
    }
    
    @GetMapping("/new")
    public String showTutorRegistrationForm(Model model) {
        
        model.addAttribute("tutor", TutorFormDto.empty());
        model.addAttribute("activePage", "clients");
        model.addAttribute("view", "tutor/tutor");
        model.addAttribute("pageScript", "/js/script.js");
        model.addAttribute("pageTitle", "Clientes");
        return "layout/base-layout";

    }

    @PostMapping
    public String saveTutor(@Valid TutorFormDto tutorDto) {

        if (tutorDto.id() != null) {

            Tutor existingTutor = tutorService.findById(tutorDto.id());

            existingTutor.updateTutorInfo(
                    new Phone(tutorDto.info().phone()),
                    tutorDto.info().name());

            tutorService.save(existingTutor);
            return "redirect:/tutor";

        }

        Tutor savedtutor = tutorService.save(TutorMapper.toEntity(tutorDto));

        return "redirect:/tutor/" + savedtutor.getId() + "/pet/new";

    }

    @GetMapping("/{id}/inactivate")
    public String deactivateTutor(@PathVariable Long id) {

        tutorService.deactivate(id);
        return "redirect:/tutor";

    }

    @GetMapping("/{id}/activate")
    public String activateTutor(@PathVariable Long id) {

        tutorService.activate(id);
        return "redirect:/tutor";

    }

    @GetMapping("/{tutorId}/pet/{petId}")
    public String findPet(@PathVariable Long tutorId, @PathVariable Long petId, Model model) {

        model.addAttribute("species", petInfoService.findAllSpecies());
        model.addAttribute("coatColors", petInfoService.findAllCoatColors());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("tutorId", tutorId);
        model.addAttribute("pet", PetMapper.toFormDto(tutorService.findByTutorAndPetId(tutorId, petId)));

        model.addAttribute("activePage", "clients");
        model.addAttribute("view", "pet/pet");
        model.addAttribute("pageScript", "/js/pet.js");
        return "layout/base-layout";

    }

    @GetMapping("/{tutorId}/pet/new")
    public String findNewPetForm(@PathVariable Long tutorId, Model model) {

        model.addAttribute("species", petInfoService.findAllSpecies());
        model.addAttribute("coatColors", petInfoService.findAllCoatColors());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("pet", PetFormDto.empty());

        model.addAttribute("activePage", "clients");
        model.addAttribute("view", "pet/pet");
        model.addAttribute("pageScript", "/js/pet.js");
        return "layout/base-layout";

    }

    @PostMapping("/{tutorId}/pet/save")
    public String savePet(@PathVariable Long tutorId, @Valid PetFormDto pet) {

        Tutor tutor = tutorService.findById(tutorId);

        if (pet.id() != null) {

            tutor.updatePetInfo(PetMapper.toEntity(pet));
            System.out.println("Atualizando...");
            System.out.println("Status = " + pet.status());
            
        }
        
        if (pet.id() == null) {
            
            tutor.addPet(PetMapper.toEntity(pet));
            System.out.println("Criando...");
            System.out.println("Status = " + pet.status());

        }

        tutorService.save(tutor);

        return "redirect:/tutor/" + tutorId;

    }

    @GetMapping("/{tutorId}/pet/{petId}/inactivate")
    public String deactivatePet(@PathVariable Long tutorId, @PathVariable Long petId) {

        tutorService.deactivatePet(tutorId, petId);
        return "redirect:/tutor/" + tutorId;

    }

    @GetMapping("/{tutorId}/pet/{petId}/activate")
    public String activatePet(@PathVariable Long tutorId, @PathVariable Long petId) {

        tutorService.activatePet(tutorId, petId);
        return "redirect:/tutor/" + tutorId;

    }
}
