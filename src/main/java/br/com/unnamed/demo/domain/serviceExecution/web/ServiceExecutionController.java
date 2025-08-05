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
    public String showServiceExecutionBoard(Model model, @RequestParam(required = false) LocalDate date) {

        date = date == null ? LocalDate.now() : date;

        List<LocalDate> datesWithNotPaid =  service.findNotPaidFromPreviousDates();

        if (!datesWithNotPaid.isEmpty())
            model.addAttribute("dates_with_not_paid", datesWithNotPaid);

        model.addAttribute("pending_services", service.findByStatusAndDate(ServiceStatus.PENDING, date));
        model.addAttribute("in_progress_services", service.findByStatusAndDate(ServiceStatus.IN_PROGRESS, date));
        model.addAttribute("completed_services", service.findByStatusAndDate(ServiceStatus.COMPLETED, date));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - EEEE");
        model.addAttribute("currentDate", date.format(formatter));
        model.addAttribute("nextDate", date.plusDays(1).toString());
        model.addAttribute("previousDate", date.minusDays(1).toString());
        model.addAttribute("currentDateISO", date.toString());
        model.addAttribute("isToday", date.isEqual(LocalDate.now()));

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecutionBoard");
        model.addAttribute("pageScript", "/js/serviceExecutionBoard.js");

        return "layout/base-layout";

    }

    @GetMapping("/new")
    public String showNewServicePage(Model model) {

        model.addAttribute("all_tutors", tutorService.findAll().stream().filter(Tutor::isActive)
                .sorted((t1, t2) -> t1.getName().compareTo(t2.getName())).toList());
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
        return "redirect:/serviceExecution" + (serviceExecution.getDate().isEqual(LocalDate.now()) ? ""
                : "?date=" + serviceExecution.getDate().toString());

    }

    @PostMapping("/{serviceId}/delete")
    public String cancelService(@PathVariable Long serviceId) {

        ServiceExecution s = service.findById(serviceId);
        service.cancel(s);

        return "redirect:/serviceExecution"
                + (s.getDate().isEqual(LocalDate.now()) ? "" : "?date=" + s.getDate().toString());

    }

    @PostMapping("/{serviceId}/start")
    public String startServiceExecution(@PathVariable Long serviceId) {

        ServiceExecution s = service.findById(serviceId);
        service.start(s);

        return "redirect:/serviceExecution"
                + (s.getDate().isEqual(LocalDate.now()) ? "" : "?date=" + s.getDate().toString());

    }

    @PostMapping("/{serviceId}/finish")
    public String finishServiceExecution(@PathVariable Long serviceId) {

        ServiceExecution s = service.findById(serviceId);
        service.finish(s);

        return "redirect:/serviceExecution"
                + (s.getDate().isEqual(LocalDate.now()) ? "" : "?date=" + s.getDate().toString());

    }

}
