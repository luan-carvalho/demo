package br.com.unnamed.demo.domain.tutor.web;

import org.springframework.data.domain.Page;
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

import br.com.unnamed.demo.domain.tutor.dto.CreatePetDto;
import br.com.unnamed.demo.domain.tutor.dto.CreateTutorDto;
import br.com.unnamed.demo.domain.tutor.dto.EditTutorDto;
import br.com.unnamed.demo.domain.tutor.dto.PetEditDto;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.service.TutorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("tutor")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @GetMapping
    public String listTutors(Model model, @RequestParam(required = false) String name,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        status = status == null ? Status.ACTIVE : status;

        Pageable pageable = PageRequest.of(page, size);
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

    @GetMapping("/new")
    public String showTutorRegistrationForm(Model model,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId) {

        // if (context != null)
        // model.addAttribute("context", context);

        // if (context != null && serviceId != null)
        // model.addAttribute("serviceId", serviceId);

        model.addAttribute("tutor", new CreateTutorDto());
        model.addAttribute("groups", tutorService.findAllGroups());
        model.addAttribute("activePage", "clients");
        model.addAttribute("mode", "create");
        model.addAttribute("view", "tutor/tutor");
        model.addAttribute("pageScript", "/js/script.js");
        model.addAttribute("pageTitle", "Cliente | Novo");
        return "layout/base-layout";

    }

    @PostMapping
    public String createTutor(@Valid CreateTutorDto tutorDto,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId,
            RedirectAttributes attributes) {

        Tutor savedtutor = tutorService.createTutor(tutorDto.name(), tutorDto.phone(), tutorDto.groupId());

        return "redirect:/tutor/" + savedtutor.getId() + "/pet/new"
                + (context != null ? "?context=" + context + (serviceId != null ? "&serviceId=" + serviceId : "") : "");

    }

    @GetMapping("/{id}")
    public String findTutorById(@PathVariable Long id, Model model) {

        EditTutorDto tutorDto = new EditTutorDto(tutorService.findById(id));
        model.addAttribute("tutor", tutorDto);
        model.addAttribute("groups", tutorService.findAllGroups());
        model.addAttribute("mode", "update");
        model.addAttribute("activePage", "clients");
        model.addAttribute("view", "tutor/tutor");
        model.addAttribute("pageScript", "/js/script.js");
        model.addAttribute("pageTitle", "Cliente | Edição");
        return "layout/base-layout";

    }

    @PostMapping("/{id}")
    public String updateTutor(@Valid EditTutorDto tutorDto,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId,
            RedirectAttributes attributes) {

        Tutor savedtutor = tutorService.updateTutorInfo(
                tutorDto.id(),
                tutorDto.name(),
                tutorDto.phone(),
                tutorDto.groupId());

        attributes.addFlashAttribute("successMessage", "Alterações salvas");
        return "redirect:/tutor/" + savedtutor.getId();

    }

    @PostMapping("/{tutorId}/createAndSetGroup")
    public String createAndSetGroup(@PathVariable Long tutorId, @RequestParam String description,
            RedirectAttributes attributes) {

        tutorService.createAndSetGroup(description, tutorId);

        attributes.addFlashAttribute("successMessage", "Alterações salvas");
        return "redirect:/tutor/" + tutorId;
    }

    @GetMapping("/{id}/inactivate")
    public String deactivateTutor(@PathVariable Long id) {

        tutorService.deactivate(id);
        return "redirect:/tutor/" + id;

    }

    @GetMapping("/{id}/activate")
    public String activateTutor(@PathVariable Long id) {

        tutorService.activate(id);
        return "redirect:/tutor/" + id;

    }

    @GetMapping("/{tutorId}/pet/{petId}")
    public String findPet(@PathVariable Long tutorId, @PathVariable Long petId, Model model) {

        PetEditDto pet = new PetEditDto(tutorService.findByTutorAndPetId(tutorId, petId));
        // List<ServiceExecution> serviceHistory =
        // service.findTop10ByPetIdOrderByDateDesc(petId);

        model.addAttribute("tutorId", tutorId);
        model.addAttribute("pet", pet);
        // model.addAttribute("serviceHistory", serviceHistory);

        model.addAttribute("mode", "update");
        model.addAttribute("pageTitle", "Pet | " + pet.name());
        model.addAttribute("activePage", "clients");
        model.addAttribute("view", "pet/pet");
        model.addAttribute("pageScript", "/js/pet.js");
        return "layout/base-layout";

    }

    @PostMapping("/{tutorId}/pet/{petId}")
    public String updatePet(@PathVariable Long tutorId,
            @PathVariable Long petId,
            @Valid PetEditDto petDto,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId,
            RedirectAttributes attributes) {

        tutorService.updatePetName(tutorId, petDto.id(), petDto.name());

        attributes.addFlashAttribute("successMessage", "Alterações salvas!");
        return "redirect:/tutor/" + tutorId + "/pet/" + petId;

    }

    @GetMapping("/{tutorId}/pet/new")
    public String findNewPetForm(@PathVariable Long tutorId, Model model,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId) {

        model.addAttribute("pet", new CreatePetDto());

        model.addAttribute("activePage", "clients");
        model.addAttribute("mode", "create");
        model.addAttribute("view", "pet/pet");
        model.addAttribute("pageTitle", "Pet | Novo");
        model.addAttribute("pageScript", "/js/pet.js");
        return "layout/base-layout";

    }

    @PostMapping("/{tutorId}/pet")
    public String createPet(@PathVariable Long tutorId,
            @Valid CreatePetDto petDto,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId,
            RedirectAttributes attributes) {

        tutorService.createPetAndSaveToTutor(tutorId, petDto.name());

        attributes.addFlashAttribute("successMessage", "Cadastro realizado!");
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
