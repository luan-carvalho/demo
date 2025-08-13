package br.com.unnamed.demo.domain.payment.web;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.unnamed.demo.domain.payment.model.PaymentMethod;
import br.com.unnamed.demo.domain.payment.service.PaymentMethodService;
import br.com.unnamed.demo.domain.payment.service.PaymentService;

@Controller
@RequestMapping("payment")
public class PaymentController {

    private final PaymentService service;
    private final PaymentMethodService typeService;

    public PaymentController(PaymentService service, PaymentMethodService typeService) {
        this.service = service;
        this.typeService = typeService;
    }

    @GetMapping
    public String all(Model model, @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) PaymentMethod type,
            @RequestParam(required = false) String name) {

        date = date == null ? LocalDate.now() : date;

        model.addAttribute("payments", service.searchWithOptionalFilters(name, date, type));
        model.addAttribute("all_payment_types", typeService.findAllActive());

        model.addAttribute("name", name);
        model.addAttribute("date", date);
        model.addAttribute("type", type);

        model.addAttribute("pageTitle", "Pagamentos");
        model.addAttribute("activePage", "pagamentos");
        model.addAttribute("view", "payment/payment-list");
        model.addAttribute("pageScript", "/js/script.js");

        return "layout/base-layout";

    }

}
