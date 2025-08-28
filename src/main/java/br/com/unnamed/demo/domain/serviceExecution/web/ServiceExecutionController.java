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

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.petCare.service.PetCareService;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
import br.com.unnamed.demo.domain.serviceExecution.service.ServiceExecutionService;
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

        List<LocalDate> datesWithNotPaid = service.findNotPaidFromPreviousDates();

        if (!datesWithNotPaid.isEmpty())
            model.addAttribute("dates_with_not_paid", datesWithNotPaid);

        model.addAttribute("pending_services", service.findByStatusAndDate(ServiceStatus.PENDING, date));
        model.addAttribute("in_progress_services", service.findByStatusAndDate(ServiceStatus.IN_PROGRESS, date));
        model.addAttribute("completed_services", service.findByStatusAndDate(ServiceStatus.COMPLETED, date));

        DateTimeFormatter formatter_long = DateTimeFormatter.ofPattern("dd/MM/yyyy - EEEE");
        DateTimeFormatter formatter_short = DateTimeFormatter.ofPattern("dd/MM/yy");
        model.addAttribute("currentDate", date.format(formatter_long));
        model.addAttribute("currentDateShort", date.format(formatter_short));
        model.addAttribute("nextDate", date.plusDays(1).toString());
        model.addAttribute("previousDate", date.minusDays(1).toString());
        model.addAttribute("currentDateISO", date.toString());
        model.addAttribute("isToday", date.isEqual(LocalDate.now()));
        model.addAttribute("pageTitle", "Atendimentos");

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecutionBoard");

        return "layout/base-layout";

    }

    @GetMapping("/new")
    public String newServicePage(Model model) {

        model.addAttribute("all_tutors", tutorService.findAllActive());
        model.addAttribute("all_pet_cares", petCareService.findAllActive());

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/newServiceExecution");
        model.addAttribute("pageTitle", "Atendimento | Novo");
        return "layout/base-layout";

    }

    @GetMapping("/{id}")
    public String newServicePage(@PathVariable Long id, Model model) {

        model.addAttribute("all_tutors", tutorService.findAllActive());
        model.addAttribute("all_pet_cares", petCareService.findAllActive());

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecution");
        model.addAttribute("pageTitle", "Atendimento | #" + id);
        return "layout/base-layout";

    }

    @PostMapping("/save")
    public String save(ServiceExecution dto) {

        ServiceExecution s = new ServiceExecution();
        service.save(s);

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
