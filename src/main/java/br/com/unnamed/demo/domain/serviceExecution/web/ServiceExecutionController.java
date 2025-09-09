package br.com.unnamed.demo.domain.serviceExecution.web;

import java.time.LocalDate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.petCare.service.PetCareService;
import br.com.unnamed.demo.domain.serviceExecution.builder.ServiceExecutionBuilder;
import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionDto;
import br.com.unnamed.demo.domain.serviceExecution.mapper.ServiceExecutionMapper;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecutionItem;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
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
    public String redirectToBoard() {

        return "redirect:/serviceExecution/board";

    }

    @GetMapping("/board")
    public String showServiceExecutionBoard(Model model) {

        model.addAttribute("existsNotPaid", service.existsNotPaid());

        model.addAttribute("pending_services", service.findByStatusAndDate(ServiceStatus.PENDING, LocalDate.now()));
        model.addAttribute("in_progress_services",
                service.findByStatusAndDate(ServiceStatus.IN_PROGRESS, LocalDate.now()));
        model.addAttribute("completed_services", service.findByStatusAndDate(ServiceStatus.COMPLETED, LocalDate.now()));

        model.addAttribute("pageTitle", "Quadro de execução");
        model.addAttribute("activePage", "serviceExecution-board");
        model.addAttribute("view", "serviceExecution/serviceExecutionBoard");

        return "layout/base-layout";

    }

    @GetMapping("/list")
    public String showServiceExecutionList(Model model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ServiceStatus status,
            @RequestParam(required = false) ServicePaymentStatus paymentStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        model.addAttribute("name", name);
        model.addAttribute("status", status);
        model.addAttribute("paymentStatus", paymentStatus);
        model.addAttribute("date", date);

        model.addAttribute("statuses", ServiceStatus.values());
        model.addAttribute("paymentStatuses", ServicePaymentStatus.values());
        model.addAttribute("existsNotPaid", service.existsNotPaid());

        model.addAttribute("services", service.searchWithOptionalFilters(name, date, status, paymentStatus, pageable));

        model.addAttribute("pageTitle", "Serviços realizados");
        model.addAttribute("activePage", "serviceExecution-list");
        model.addAttribute("view", "serviceExecution/serviceExecutionList");

        return "layout/base-layout";

    }

    @GetMapping("/list/pendingPayment")
    public String showServicesWithPendingPayment() {

        return "redirect:/serviceExecution/list?paymentStatus=" + ServicePaymentStatus.NOT_PAID;

    }

    @GetMapping("/new")
    public String newServicePage(Model model) {

        model.addAttribute("all_tutors", tutorService.findAllActive());
        model.addAttribute("all_pet_care_groups", petCareService.findAllGroups());
        model.addAttribute("serviceExecution", ServiceExecutionDto.empty());

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecution");
        model.addAttribute("pageTitle", "Atendimento | Novo");
        return "layout/base-layout";

    }

    @GetMapping("/{id}")
    public String newServicePage(@PathVariable Long id, Model model) {

        ServiceExecution s = service.findById(id);

        model.addAttribute("all_tutors", tutorService.findAllActive());
        model.addAttribute("all_pet_care_groups", petCareService.findAllGroups());

        model.addAttribute("serviceExecution", ServiceExecutionMapper.toDto(s));

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecution");
        model.addAttribute("pageTitle", "Atendimento | #" + id);
        return "layout/base-layout";

    }

    @PostMapping("/save")
    public String save(ServiceExecutionDto serviceExecution) {

        Tutor t = tutorService.findById(Long.valueOf(1));
        Pet p = tutorService.findByTutorAndPetId(Long.valueOf(1), Long.valueOf(1));

        ServiceExecutionBuilder builder = new ServiceExecutionBuilder();

        builder.id(serviceExecution.id());
        builder.date(serviceExecution.date());
        builder.status(serviceExecution.status());
        builder.pet(p);
        builder.tutor(t);
        builder.items(serviceExecution.selectedPetCareIds()
                .stream()
                .map(id -> new ServiceExecutionItem(petCareService.findById(id)))
                .toList());
        builder.payments(serviceExecution.payments());
        builder.paymentStatus(serviceExecution.paymentStatus());

        service.save(builder.build());

        return "redirect:/serviceExecution";
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

    @GetMapping("/{serviceId}/checkout")
    public String sendServiceExecutionToRegister(@PathVariable Long serviceId, Model model) {

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecutionCheckout");
        model.addAttribute("pageTitle", "Pagamento | Atendimento #" + serviceId);

        return "layout/base-layout";

    }

    @PostMapping("/{serviceId}/addPayment")
    public String addPaymentToServiceExecution(@PathVariable Long serviceId, Payment payment) {

        ServiceExecution s = service.findById(serviceId);
        service.addPayment(s, payment);

        return "redirect:/serviceExecution/" + serviceId + "/checkout";

    }
}
