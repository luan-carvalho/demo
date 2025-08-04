package br.com.unnamed.demo.domain.serviceExecution.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import br.com.unnamed.demo.domain.petCare.service.PetCareService;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
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

        model.addAttribute("pending_services", service.findByStatus(ServiceStatus.PENDING));
        model.addAttribute("in_progress_services", service.findByStatus(ServiceStatus.IN_PROGRESS));
        model.addAttribute("completed_services", service.findByStatus(ServiceStatus.COMPLETED));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy");
        model.addAttribute("currentDate", LocalDate.now().format(formatter));

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecutionBoard");
        model.addAttribute("pageScript", "/js/serviceExecutionBoard.js");

        return "layout/base-layout";

    }

    @GetMapping("/new")
    public String showNewServicePage(Model model) {

        model.addAttribute("all_tutors", tutorService.findAllActive());
        model.addAttribute("all_pet_cares", petCareService.findAllActive());

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/newServiceExecution");
        model.addAttribute("pageScript", "/js/newServiceExecution.js");
        return "layout/base-layout";

    }

    @GetMapping("/{serviceId}")
    public String showEditServicePage(@PathVariable Long serviceId, Model model) {

        model.addAttribute("serviceExecution", service.findById(serviceId));
        model.addAttribute("all_pet_cares", petCareService.findAllActive());

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/editServiceExecution");
        model.addAttribute("pageScript", "/js/editServiceExecution.js");
        return "layout/base-layout";

    }

    @PostMapping("/new")
    public String createNewService(Long tutorId, Long petId, @RequestParam(required = false) List<Long> petCareIds) {

        List<PetCare> petCares = petCareIds.stream().map(i -> petCareService.findById(i)).toList();
        Tutor tutor = tutorService.findById(tutorId);
        Pet pet = tutor.getOwnedPet(petId);

        service.create(tutor, pet, petCares);
        return "redirect:/serviceExecution";
    }

    // --- "EDIT" FORM SUBMISSION MAPPING ---
    @PostMapping("/update")
    public String updateService(Long serviceId, @RequestParam(required = false) List<Long> petCareIds) {

        ServiceExecution serviceExecution = service.findById(serviceId);
        List<PetCare> petCares = petCareIds.stream().map(i -> petCareService.findById(i)).toList();
        service.update(serviceExecution, petCares);
        return "redirect:/serviceExecution";

    }

    @PostMapping("/{serviceId}/delete")
    public String cancelService(@PathVariable Long serviceId) {

        service.cancel(serviceId);

        return "redirect:/serviceExecution";

    }

    @PostMapping("/{serviceId}/start")
    public String startServiceExecution(@PathVariable Long serviceId) {

        service.start(serviceId);

        return "redirect:/serviceExecution";

    }

    @PostMapping("/{serviceId}/finish")
    public String finishServiceExecution(@PathVariable Long serviceId) {

        service.finish(serviceId);

        return "redirect:/serviceExecution";

    }

}
