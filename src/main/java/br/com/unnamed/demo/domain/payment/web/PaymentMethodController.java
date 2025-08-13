package br.com.unnamed.demo.domain.payment.web;

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

import br.com.unnamed.demo.domain.payment.dto.PaymentMethodDto;
import br.com.unnamed.demo.domain.payment.mapper.PaymentMethodMapper;
import br.com.unnamed.demo.domain.payment.model.PaymentMethod;
import br.com.unnamed.demo.domain.payment.model.enums.PaymentMethodType;
import br.com.unnamed.demo.domain.payment.service.PaymentMethodService;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Controller
@RequestMapping("paymentType")
public class PaymentMethodController {

    private final PaymentMethodService service;

    public PaymentMethodController(PaymentMethodService service) {
        this.service = service;
    }

    @GetMapping
    public String listPaymentMethods(Model model,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) PaymentMethodType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        status = status == null ? Status.ACTIVE : status;

        Pageable pageable = PageRequest.of(page, size, Sort.by("description").ascending());

        model.addAttribute("typePage", service.searchWithOptionalFilters(description, status, pageable));
        model.addAttribute("status", status);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("description", description);
        model.addAttribute("view", "paymentType/paymentType-list");
        model.addAttribute("activePage", "tipo-pagamento");
        model.addAttribute("pageScript", "/js/script.js");
        model.addAttribute("pageTitle", "Tipo de pagamento");
        return "layout/base-layout";

    }

    @GetMapping("/new")
    public String newPaymentMethodForm(Model model) {

        model.addAttribute("paymentType", PaymentMethodDto.empty());
        model.addAttribute("view", "paymentType/paymentType");
        model.addAttribute("activePage", "tipo-pagamento");
        model.addAttribute("pageTitle", "Tipo de pagamento | Novo");
        return "layout/base-layout";

    }

    @GetMapping("/{id}")
    public String getPaymentMethod(@PathVariable Long id, Model model) {

        PaymentMethod p = service.findById(id);

        model.addAttribute("paymentType", p);
        model.addAttribute("view", "paymentType/paymentType");
        model.addAttribute("activePage", "tipo-pagamento");
        model.addAttribute("pageTitle", "Tipo de pagamento | " + p.getDescription());
        return "layout/base-layout";
    }

    @PostMapping
    public String savePaymentMethod(PaymentMethodDto paymentType) {

        if (paymentType.id() != null) {

            PaymentMethod existingPaymentMethod = service.findById(paymentType.id());
            existingPaymentMethod.updateDescription(paymentType.description());
            service.save(existingPaymentMethod);
            return "redirect:/paymentType";

        }

        PaymentMethod p = PaymentMethodMapper.toEntity(paymentType);
        p.activate();

        service.save(p);

        return "redirect:/paymentType";

    }

    @GetMapping("/{id}/inactivate")
    public String deactivatePaymentMethod(@PathVariable Long id) {

        service.deactivate(id);
        return "redirect:/paymentType";

    }

    @GetMapping("/{id}/activate")
    public String activatePaymentMethod(@PathVariable Long id) {

        service.activate(id);
        return "redirect:/paymentType";

    }

}
