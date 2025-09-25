package br.com.unnamed.demo.domain.tutor.web;

import java.time.LocalDate;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.unnamed.demo.domain.serviceExecution.builder.ServiceExecutionBuilder;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.serviceExecution.service.ServiceExecutionService;
import br.com.unnamed.demo.domain.tutor.dtos.PetFormDto;
import br.com.unnamed.demo.domain.tutor.dtos.TutorFormDto;
import br.com.unnamed.demo.domain.tutor.mapper.PetMapper;
import br.com.unnamed.demo.domain.tutor.mapper.TutorMapper;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Phone;
import br.com.unnamed.demo.domain.tutor.service.TutorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("tutor")
public class TutorController {

    private final TutorService tutorService;
    private final ServiceExecutionService service;

    public TutorController(TutorService tutorService, ServiceExecutionService service) {
        this.tutorService = tutorService;
        this.service = service;
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
        model.addAttribute("pageTitle", "Cliente | " + tutorDto.info().name().split(" ")[0]);
        return "layout/base-layout";

    }

    @GetMapping("/new")
    public String showTutorRegistrationForm(Model model,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId) {

        if (context != null)
            model.addAttribute("context", context);

        if (context != null && serviceId != null)
            model.addAttribute("serviceId", serviceId);

        model.addAttribute("tutor", TutorFormDto.empty());
        model.addAttribute("activePage", "clients");
        model.addAttribute("view", "tutor/tutor");
        model.addAttribute("pageScript", "/js/script.js");
        model.addAttribute("pageTitle", "Cliente | Novo");
        return "layout/base-layout";

    }

    @PostMapping
    public String saveTutor(@Valid TutorFormDto tutorDto,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId) {

        if (tutorDto.id() != null) {

            Tutor existingTutor = tutorService.findById(tutorDto.id());

            existingTutor.updateTutorInfo(
                    new Phone(tutorDto.info().phone()),
                    tutorDto.info().name());

            tutorService.save(existingTutor);
            return "redirect:/tutor";

        }

        Tutor savedtutor = tutorService.save(TutorMapper.toEntity(tutorDto));

        return "redirect:/tutor/" + savedtutor.getId() + "/pet/new"
                + (context != null ? "?context=" + context + (serviceId != null ? "&serviceId=" + serviceId : "") : "");

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

        PetFormDto pet = PetMapper.toFormDto(tutorService.findByTutorAndPetId(tutorId, petId));
        List<ServiceExecution> serviceHistory = service.findTop10ByPetIdOrderByDateDesc(petId);

        model.addAttribute("tutorId", tutorId);
        model.addAttribute("pet", pet);
        model.addAttribute("serviceHistory", serviceHistory);

        model.addAttribute("pageTitle", "Pet | " + pet.name());
        model.addAttribute("activePage", "clients");
        model.addAttribute("view", "pet/pet");
        model.addAttribute("pageScript", "/js/pet.js");
        return "layout/base-layout";

    }

    @GetMapping("/{tutorId}/pet/new")
    public String findNewPetForm(@PathVariable Long tutorId, Model model,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId) {

        model.addAttribute("pet", PetFormDto.empty());

        if (context != null)
            model.addAttribute("context", context);

        if (context != null && serviceId != null)
            model.addAttribute("serviceId", serviceId);

        model.addAttribute("activePage", "clients");
        model.addAttribute("view", "pet/pet");
        model.addAttribute("pageTitle", "Pet | Novo");
        model.addAttribute("pageScript", "/js/pet.js");
        return "layout/base-layout";

    }

    @PostMapping("/{tutorId}/pet/save")
    public String savePet(@PathVariable Long tutorId, @Valid PetFormDto petDto,
            @RequestParam(required = false) String context,
            @RequestParam(required = false) Long serviceId, RedirectAttributes attributes) {

        Tutor tutor = tutorService.findById(tutorId);
        Pet pet = PetMapper.toEntity(petDto);

        if (petDto.id() != null) {

            tutor.updatePetInfo(pet);

        }

        if (petDto.id() == null) {

            tutor.addPet(pet);
            pet = tutorService.save(pet);

        }

        tutor = tutorService.save(tutor);

        if (context != null) {

            if (context.equals("update") && serviceId != null) {

                ServiceExecution toBeUpdated = service.findById(serviceId);
                toBeUpdated.updateTutorAndPet(tutor, pet);
                service.save(toBeUpdated);

                attributes.addFlashAttribute("successMessage", "Atendimento atualizado com sucesso!");
                return "redirect:/serviceExecution/" + toBeUpdated.getId();

            }

            if (context.equals("create")) {

                ServiceExecution created = this.service.save(
                        new ServiceExecutionBuilder()
                                .tutor(tutor)
                                .pet(pet)
                                .date(LocalDate.now())
                                .status(ServiceStatus.PENDING)
                                .paymentStatus(ServicePaymentStatus.NOT_PAID)
                                .build());

                attributes.addFlashAttribute("successMessage", "Atendimento criado com sucesso!");
                return "redirect:/serviceExecution/" + created.getId();

            }

        }

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
