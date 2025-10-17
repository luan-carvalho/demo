package br.com.unnamed.demo.domain.tutor.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.unnamed.demo.domain.serviceExecution.facade.ServiceExecutionFacade;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.tutor.dto.CreatePetDto;
import br.com.unnamed.demo.domain.tutor.dto.PetEditDto;
import br.com.unnamed.demo.domain.tutor.service.TutorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("tutor/{tutorId}/pet")
public class PetController {

    private final TutorService tutorService;
    private final ServiceExecutionFacade facade;

    public PetController(TutorService tutorService, ServiceExecutionFacade facade) {
        this.tutorService = tutorService;
        this.facade = facade;
    }

    @GetMapping("/{petId}")
    public String findPet(@PathVariable Long tutorId, @PathVariable Long petId,
            Model model) {

        PetEditDto pet = new PetEditDto(tutorService.findByTutorAndPetId(tutorId,
                petId));
        List<ServiceExecution> serviceHistory = facade.findTop10ByPetIdOrderByDateDesc(petId);

        model.addAttribute("tutorId", tutorId);
        model.addAttribute("tutorName", tutorService.findById(tutorId).getName());
        model.addAttribute("pet", pet);
        model.addAttribute("serviceHistory", serviceHistory);

        model.addAttribute("mode", "update");
        model.addAttribute("pageTitle", "Pet | " + pet.name());
        model.addAttribute("activePage", "clients");
        model.addAttribute("view", "pet/pet");
        
        return "layout/base-layout";

    }

    @PostMapping("/{petId}")
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

    @GetMapping("/new")
    public String findNewPetForm(@PathVariable Long tutorId, Model model,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId) {

        model.addAttribute("pet", new CreatePetDto());

        if (context != null && context.equals("create"))
            model.addAttribute("context", context);

        if (context != null && context.equals("update") && serviceId != null) {
            model.addAttribute("serviceId", serviceId);
            model.addAttribute("context", context);

        }

        model.addAttribute("tutorName", tutorService.findById(tutorId).getName());
        model.addAttribute("activePage", "clients");
        model.addAttribute("mode", "create");
        model.addAttribute("view", "pet/pet");
        model.addAttribute("pageTitle", "Pet | Novo");
        
        return "layout/base-layout";

    }

    @PostMapping
    public String createPet(@PathVariable Long tutorId,
    @Valid CreatePetDto petDto,
    @RequestParam(required = false) String context,
    @RequestParam(required = false) Long serviceId,
    RedirectAttributes attributes) {

    if (context != null && context.equals("create")) {

    ServiceExecution created = facade.createServiceExecutionWithNewPet(tutorId,
    petDto.name());
    attributes.addFlashAttribute("successMessage", "Atendimento criado com sucesso!");
    return "redirect:/serviceExecution/" + created.getId();

    }

    if (context != null && context.equals("update") && serviceId != null) {

    facade.updateClientWithNewPet(serviceId, tutorId, petDto.name());
    attributes.addFlashAttribute("successMessage", "Alterações salvas!");
    return "redirect:/serviceExecution/" + serviceId;

    }

    tutorService.createPetAndSaveToTutor(tutorId, petDto.name());
    attributes.addFlashAttribute("successMessage", "Cadastro realizado!");
    return "redirect:/tutor/" + tutorId;

    }

    @PostMapping("/{petId}/inactivate")
    public String deactivatePet(@PathVariable Long tutorId, @PathVariable Long petId, RedirectAttributes attributes) {

        tutorService.deactivatePet(tutorId, petId);
        attributes.addFlashAttribute("successMessage", "Pet inativado com sucesso!");
        return "redirect:/tutor/" + tutorId;

    }

    @PostMapping("/{petId}/activate")
    public String activatePet(@PathVariable Long tutorId, @PathVariable Long petId, RedirectAttributes attributes) {

        tutorService.activatePet(tutorId, petId);
        attributes.addFlashAttribute("successMessage", "Pet ativado com sucesso!");
        return "redirect:/tutor/" + tutorId;

    }

}
