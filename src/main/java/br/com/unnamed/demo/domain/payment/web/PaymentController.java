package br.com.unnamed.demo.domain.payment.web;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.unnamed.demo.domain.payment.dto.PaymentDtos.PaymentDto;
import br.com.unnamed.demo.domain.payment.mapper.PaymentMapper;
import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentType;
import br.com.unnamed.demo.domain.payment.service.PaymentService;
import br.com.unnamed.demo.domain.payment.service.PaymentTypeService;

@Controller
@RequestMapping("payment")
public class PaymentController {

    private final PaymentService service;
    private final PaymentTypeService typeService;

    public PaymentController(PaymentService service, PaymentTypeService typeService) {
        this.service = service;
        this.typeService = typeService;
    }

    @GetMapping
    public String all(Model model, @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) PaymentType type,
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

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {

        model.addAttribute("all_payment_types", typeService.findAllActive());
        model.addAttribute("payment", PaymentMapper.toDto(service.findById(id)));
        model.addAttribute("view", "payment/payment");
        model.addAttribute("activePage", "pagamentos");
        model.addAttribute("pageTitle", "Pagamento");
        return "layout/base-layout";
    }

    @PostMapping
    public String save(PaymentDto paymentDto) {

        Payment p = service.findById(paymentDto.getId());
        p.updateAmountAndType(paymentDto.getValue(), paymentDto.getType());
        service.save(p);
        return "redirect:/payment/" + paymentDto.getId();

    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        service.delete(id);
        return "redirect:/payment";

    }

}
