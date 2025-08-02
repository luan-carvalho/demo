package br.com.unnamed.demo.domain.serviceExecution.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.petCare.service.PetCareService;
import br.com.unnamed.demo.domain.serviceExecution.service.ServiceExecutionService;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.service.TutorService;

@Controller
@RequestMapping("serviceExecution")
public class ServiceExecutionController {

    private ServiceExecutionService service;
    private PetCareService petCareService;
    private TutorService tutorService;

    public ServiceExecutionController(ServiceExecutionService service, PetCareService petCareService,
            TutorService tutorService) {
        this.service = service;
        this.petCareService = petCareService;
        this.tutorService = tutorService;
    }

    @GetMapping
    public String showServiceExecutionBoard(Model model) {

        model.addAttribute("pending_services", service.findAllPending(LocalDate.now()));
        model.addAttribute("in_progress_services", service.findAllInProgress(LocalDate.now()));
        model.addAttribute("completed_services", service.findAllCompleted(LocalDate.now()));

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecution");
        return "layout/base-layout";

    }

    @GetMapping("/new")
    public String showNewServicePage(Model model) {

        model.addAttribute("all_tutors", tutorService.findAllActive());
        model.addAttribute("all_pet_cares", petCareService.findAllActive());

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/newServiceExecution");
        return "layout/base-layout";
    }

    @PostMapping("/new")
    public String createNewService(Long tutorId, Long petId, List<Long> petCareIds) {

        Tutor tutor = tutorService.findById(tutorId);
        Pet pet = tutor.getOwnedPet(petId);
        List<PetCare> petCares = new ArrayList<>();
        petCareIds.forEach(id -> petCares.add(petCareService.findById(id)));

        service.createServiceExecution(tutor, pet, petCares);

        return "redirect:/serviceExecution";
    }

}
