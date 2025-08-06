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

import br.com.unnamed.demo.domain.payment.dto.PaymentTypeDto;
import br.com.unnamed.demo.domain.payment.mapper.PaymentTypeMapper;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentType;
import br.com.unnamed.demo.domain.payment.service.PaymentTypeService;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Controller
@RequestMapping("paymentType")
public class PaymentTypeController {

    private final PaymentTypeService service;

    public PaymentTypeController(PaymentTypeService service) {
        this.service = service;
    }

    @GetMapping
    public String listPaymentTypes(Model model,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Status status,
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
    public String newPaymentTypeForm(Model model) {

        model.addAttribute("paymentType", PaymentTypeDto.empty());
        model.addAttribute("view", "paymentType/paymentType");
        model.addAttribute("activePage", "tipo-pagamento");
        model.addAttribute("pageTitle", "Tipo de pagamento");
        return "layout/base-layout";

    }

    @GetMapping("/{id}")
    public String getPaymentType(@PathVariable Long id, Model model) {

        model.addAttribute("paymentType", service.findById(id));
        model.addAttribute("view", "paymentType/paymentType");
        model.addAttribute("activePage", "tipo-pagamento");
        model.addAttribute("pageTitle", "Tipo de pagamento");
        return "layout/base-layout";
    }

    @PostMapping
    public String savePaymentType(PaymentTypeDto paymentType) {

        if (paymentType.id() != null) {

            PaymentType existingPaymentType = service.findById(paymentType.id());
            existingPaymentType.updateDescription(paymentType.description());
            service.save(existingPaymentType);
            return "redirect:/paymentType";

        }

        PaymentType p = PaymentTypeMapper.toEntity(paymentType);
        p.activate();

        service.save(p);

        return "redirect:/paymentType";

    }

    @GetMapping("/{id}/inactivate")
    public String deactivatePaymentType(@PathVariable Long id) {

        service.deactivate(id);
        return "redirect:/paymentType";

    }

    @GetMapping("/{id}/activate")
    public String activatePaymentType(@PathVariable Long id) {

        service.activate(id);
        return "redirect:/paymentType";

    }

}
