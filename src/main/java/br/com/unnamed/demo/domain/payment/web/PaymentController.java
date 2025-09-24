package br.com.unnamed.demo.domain.payment.web;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.payment.service.PaymentService;

@Controller
@RequestMapping("payment")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public String all(Model model,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long methodId,
            @RequestParam(required = false) String name) {

        PaymentMethod method = methodId != null ? service.findPaymentMethodById(methodId) : null;

        model.addAttribute("payments", service.searchWithOptionalFilters(name, date, method));
        model.addAttribute("all_payment_methods", service.getAllPaymentMethods());

        model.addAttribute("name", name);
        model.addAttribute("date", date);
        model.addAttribute("method", method);

        model.addAttribute("pageTitle", "Pagamentos");
        model.addAttribute("activePage", "pagamentos");
        model.addAttribute("view", "payment/payment-list");

        return "layout/base-layout";

    }

}
