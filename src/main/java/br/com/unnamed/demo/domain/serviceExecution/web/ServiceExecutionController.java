package br.com.unnamed.demo.domain.serviceExecution.web;

import java.time.LocalDate;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionDto;
import br.com.unnamed.demo.domain.serviceExecution.facade.ServiceExecutionFacade;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Controller
@RequestMapping("serviceExecution")
public class ServiceExecutionController {

    private final ServiceExecutionFacade facade;

    public ServiceExecutionController(ServiceExecutionFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String showServiceExecutionBoard(Model model) {

        model.addAttribute("existsNotPaid", facade.existsNotPaid());

        model.addAttribute("pending_services",
                facade.findByStatusAndDate(ServiceStatus.PENDING));
        model.addAttribute("in_progress_services",
                facade.findByStatusAndDate(ServiceStatus.IN_PROGRESS));
        model.addAttribute("completed_services",
                facade.findByStatusAndDate(ServiceStatus.DONE));

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
        model.addAttribute("existsNotPaid", facade.existsNotPaid());

        model.addAttribute("services", facade.searchWithOptionalFilters(name, date,
                status, paymentStatus, pageable));

        model.addAttribute("pageTitle", "Serviços realizados");
        model.addAttribute("activePage", "serviceExecution-list");
        model.addAttribute("view", "serviceExecution/serviceExecutionList");

        return "layout/base-layout";

    }

    @GetMapping("/list/pendingPayment")
    public String showServicesWithPendingPayment() {

        return "redirect:/serviceExecution/list?paymentStatus=" +
                ServicePaymentStatus.NOT_PAID;

    }

    @GetMapping("/new")
    public String newServicePage(Model model) {

        return "redirect:/serviceExecution/clientSelection?context=create";

    }

    @PostMapping
    public String createNewServiceExecution(Model model, Long tutorId,
            @RequestParam(required = false) Long petId,
            @RequestParam(required = false) String petName, RedirectAttributes attributes) {

        if (petId == null && petName != null && !petName.isBlank()) {

            ServiceExecution created = facade.createServiceExecutionWithNewPet(tutorId, petName);
            attributes.addFlashAttribute("successMessage", "Atendimento criado com sucesso!");
            return "redirect:/serviceExecution/" + created.getId();

        }

        ServiceExecution created = facade.createServiceExecution(tutorId, petId);

        attributes.addFlashAttribute("successMessage", "Atendimento criado com sucesso!");
        return "redirect:/serviceExecution/" + created.getId();

    }

    @GetMapping("/{id}")
    public String getServiceExecution(@PathVariable Long id, Model model) {

        ServiceExecution s = facade.findServiceExecutionById(id);

        model.addAttribute("serviceExecution", new ServiceExecutionDto(s));
        model.addAttribute("petCareGroups", facade.findAllPetCareGroups());

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
        Page<Tutor> tutorPage = facade.searchClientshWithOptionalFilters(search,
                pageable, Status.ACTIVE);

        model.addAttribute("tutorsPage", tutorPage);
        model.addAttribute("search", search);
        model.addAttribute("context", context);
        model.addAttribute("serviceExecutionId", serviceExecutionId);

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/clientSelection");
        model.addAttribute("pageTitle", "Atendimento | Cliente");
        return "layout/base-layout";

    }

    @PostMapping("/{serviceExecutionId}/updateClient")
    public String updateClient(@PathVariable Long serviceExecutionId, Long tutorId,
            @RequestParam(required = false) Long petId,
            @RequestParam(required = false) String petName, RedirectAttributes attributes) {

        if (petId == null && petName != null && !petName.isBlank()) {

            facade.updateClientWithNewPet(serviceExecutionId, tutorId, petName);
            attributes.addFlashAttribute("successMessage", "Atendimento atualizado com sucesso!");
            return "redirect:/serviceExecution/" + serviceExecutionId;

        }

        facade.updateClient(serviceExecutionId, tutorId, petId);

        attributes.addFlashAttribute("successMessage", "Atendimento atualizado com sucesso!");
        return "redirect:/serviceExecution/" + serviceExecutionId;

    }

    @PostMapping("/{serviceExecutionId}")
    public String save(@PathVariable Long serviceExecutionId, Model model, @RequestParam(required = false) String obs,
            @RequestParam(required = false) List<Long> selectedItems,
            RedirectAttributes attributes) {

        if (selectedItems != null || (obs != null && !obs.isBlank())) {

            facade.updateServiceExecution(serviceExecutionId, selectedItems, obs);
            attributes.addFlashAttribute("successMessage", "Atendimento atualizado com sucesso!");

        }

        return "redirect:/serviceExecution/" + serviceExecutionId;

    }

    @PostMapping("/{serviceId}/cancel")
    public String cancelService(@PathVariable Long serviceId, RedirectAttributes attributes) {

        if (facade.isServiceExecutionEmpty(serviceId)) {

            facade.cancelServiceExecution(serviceId);
            attributes.addFlashAttribute("errorMessage", "Atendimento cancelado!");
            return "redirect:/serviceExecution";

        }

        facade.cancelServiceExecution(serviceId);
        attributes.addFlashAttribute("errorMessage", "Atendimento cancelado!");
        return "redirect:/serviceExecution/" + serviceId;

    }

    @PostMapping("/{serviceId}/start")
    public String startServiceExecution(@PathVariable Long serviceId, RedirectAttributes attributes) {

        facade.startServiceExecution(serviceId);
        attributes.addFlashAttribute("successMessage", "Atendimento iniciado!");
        return "redirect:/serviceExecution";

    }

    @PostMapping("/{serviceId}/markAsDone")
    public String markAsDone(@PathVariable Long serviceId, RedirectAttributes attributes) {

        facade.markServiceExecutionAsDone(serviceId);
        attributes.addFlashAttribute("successMessage", "Atendimento finalizado!");
        return "redirect:/serviceExecution";

    }

}
