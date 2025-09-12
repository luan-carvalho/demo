package br.com.unnamed.demo.domain.serviceExecution.web;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
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

import br.com.unnamed.demo.domain.payment.mapper.PaymentMapper;
import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.enums.PaymentStatus;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.payment.service.PaymentService;
import br.com.unnamed.demo.domain.petCare.service.PetCareService;
import br.com.unnamed.demo.domain.serviceExecution.builder.ServiceExecutionBuilder;
import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionCheckoutDto;
import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionDto;
import br.com.unnamed.demo.domain.serviceExecution.mapper.ServiceExecutionMapper;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecutionItem;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.serviceExecution.service.ServiceExecutionService;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.service.TutorService;

@Controller
@RequestMapping("serviceExecution")
public class ServiceExecutionController {

    private final ServiceExecutionService service;
    private final PetCareService petCareService;
    private final TutorService tutorService;
    private final PaymentService paymentService;

    public ServiceExecutionController(ServiceExecutionService service, PetCareService petCareService,
            TutorService tutorService, PaymentService paymentService) {
        this.service = service;
        this.petCareService = petCareService;
        this.tutorService = tutorService;
        this.paymentService = paymentService;
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
        model.addAttribute("completed_services", service.findByStatusAndDate(ServiceStatus.DONE, LocalDate.now()));

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

        return "redirect:/serviceExecution/clientSelection?context=create";

    }

    @PostMapping("/create")
    public String createNewServiceExecution(Model model, Long tutorId, @RequestParam(required = false) Long petId,
            @RequestParam(required = false) String petName) {

        Tutor t = tutorService.findById(tutorId);
        Pet p = null;

        if (petId == null && petName != null && !petName.isBlank()) {

            p = new Pet(null, petName, Status.ACTIVE);
            t.addPet(p);
            p = tutorService.save(p);
            t = tutorService.save(t);

        }

        if (petId != null) {

            p = t.getOwnedPet(petId);

        }

        ServiceExecution created = service.save(
                new ServiceExecutionBuilder()
                        .tutor(t)
                        .pet(p)
                        .date(LocalDate.now())
                        .status(ServiceStatus.PENDING)
                        .paymentStatus(ServicePaymentStatus.NOT_PAID)
                        .build());

        return "redirect:/serviceExecution/" + created.getId();

    }

    @GetMapping("/{id}")
    public String newServicePage(@PathVariable Long id, Model model) {

        ServiceExecution s = service.findById(id);

        model.addAttribute("all_tutors", tutorService.findAllActive());
        model.addAttribute("all_pet_care_groups", petCareService.findAllGroups());

        model.addAttribute("serviceExecution", ServiceExecutionMapper.toDto(s));
        model.addAttribute("payments", s.getPayments().stream().map(p -> PaymentMapper.toSimpleListDto(p)).toList());

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecution");
        model.addAttribute("pageTitle", "Atendimento | #" + id);
        return "layout/base-layout";

    }

    @GetMapping("/clientSelection")
    public String updateClient(Model model,
            @RequestParam(required = false) String search,
            @RequestParam String context,
            @RequestParam(required = false) Long serviceExecutionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Tutor> tutorPage = tutorService.searchWithOptionalFilters(search, pageable, Status.ACTIVE);

        model.addAttribute("tutorsPage", tutorPage);
        model.addAttribute("search", search);
        model.addAttribute("context", context);
        model.addAttribute("serviceExecutionId", serviceExecutionId);

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/clientSelection");
        model.addAttribute("pageTitle", "Atendimento | Cliente");
        return "layout/base-layout";

    }

    @PostMapping("/updateClient")
    public String updateClient(Long serviceExecutionId, Long tutorId, @RequestParam(required = false) Long petId,
            @RequestParam(required = false) String petName) {

        ServiceExecution toBeUpdated = service.findById(serviceExecutionId);
        Tutor t = tutorService.findById(tutorId);
        Pet p = null;

        if (petId == null && petName != null && !petName.isBlank()) {

            p = new Pet(null, petName, Status.ACTIVE);
            t.addPet(p);
            p = tutorService.save(p);
            t = tutorService.save(t);

        }

        if (petId != null) {

            p = t.getOwnedPet(petId);

        }

        toBeUpdated.updateTutorAndPet(t, p);

        service.save(toBeUpdated);

        return "redirect:/serviceExecution/" + serviceExecutionId;

    }

    @PostMapping("/save")
    public String save(Model model, ServiceExecutionDto s) {

        if (!s.selectedPetCareIds().isEmpty()) {

            ServiceExecution toBeUpdated = service.findById(s.id());
            toBeUpdated.updateExecutedServices(s.selectedPetCareIds().stream()
                    .map(id -> new ServiceExecutionItem(petCareService.findById(id))).collect(Collectors.toList()));

            service.save(toBeUpdated);

        }

        return "redirect:/serviceExecution/" + s.id();

    }

    @PostMapping("/{serviceId}/cancel")
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

    @PostMapping("/{serviceId}/markAsDone")
    public String markAsDone(@PathVariable Long serviceId) {

        ServiceExecution s = service.findById(serviceId);
        service.finish(s);

        return "redirect:/serviceExecution"
                + (s.getDate().isEqual(LocalDate.now()) ? "" : "?date=" + s.getDate().toString());

    }

    @GetMapping("/{serviceId}/checkout")
    public String checkout(@PathVariable Long serviceId, Model model) {

        ServiceExecutionCheckoutDto s = ServiceExecutionMapper.toCheckoutDto(service.findById(serviceId));

        model.addAttribute("serviceExecution", s);
        model.addAttribute("all_payment_types", paymentService.getAllPaymentMethods());

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecutionCheckout");
        model.addAttribute("pageTitle", "Pagamento | Atendimento #" + serviceId);

        return "layout/base-layout";

    }

    @PostMapping("/{serviceId}/addPayment")
    public String addPaymentToServiceExecution(@PathVariable Long serviceId, Long typeId, BigDecimal amount,
            @RequestParam(required = false) String obs) {

        ServiceExecution s = service.findById(serviceId);
        PaymentMethod type = paymentService.findPaymentMethodById(typeId);

        PaymentStatus status = s.getServiceStatus() != ServiceStatus.COMPLETED ? PaymentStatus.TEMPORARY
                : PaymentStatus.FINAL;

        Payment p = new Payment(null, LocalDate.now(), s, type, amount, status, obs);
        s.addPayment(p);
        service.save(s);

        return "redirect:/serviceExecution/" + serviceId + "/checkout";

    }

    @PostMapping("/{serviceId}/checkout/finish")
    public String finishServiceExecution(@PathVariable Long serviceId, Model model) {

        ServiceExecution s = service.findById(serviceId);
        s.markAsPaid();
        service.save(s);

        return "redirect:/serviceExecution";

    }

}
