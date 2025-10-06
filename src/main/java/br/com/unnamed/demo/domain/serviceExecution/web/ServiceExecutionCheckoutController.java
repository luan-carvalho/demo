package br.com.unnamed.demo.domain.serviceExecution.web;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.enums.PaymentStatus;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.payment.service.PaymentService;
import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionCheckoutDto;
import br.com.unnamed.demo.domain.serviceExecution.mapper.ServiceExecutionMapper;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.service.ServiceExecutionService;

@Controller
@RequestMapping("serviceExecution/{serviceId}/checkout")
public class ServiceExecutionCheckoutController {

    private final ServiceExecutionService service;
    private final PaymentService paymentService;

    public ServiceExecutionCheckoutController(ServiceExecutionService service, PaymentService paymentService) {
        this.service = service;
        this.paymentService = paymentService;
    }

    @GetMapping
    public String checkout(@PathVariable Long serviceId, Model model) {

        ServiceExecutionCheckoutDto s = ServiceExecutionMapper.toCheckoutDto(service.findById(serviceId));

        model.addAttribute("serviceExecution", s);
        model.addAttribute("all_payment_types", paymentService.getAllPaymentMethods());

        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecutionCheckout");
        model.addAttribute("pageTitle", "Pagamento | Atendimento #" + serviceId);

        return "layout/base-layout";

    }

    @PostMapping("/addPayment")
    public String addPaymentToServiceExecution(@PathVariable Long serviceId, Long typeId, BigDecimal amount,
            @RequestParam(required = false) String obs, @RequestParam(required = false) Integer installments,
            RedirectAttributes attributes) {

        ServiceExecution s = service.findById(serviceId);
        PaymentMethod method = paymentService.findPaymentMethodById(typeId);

        service.addPayment(s, method, installments, amount, obs);
        attributes.addFlashAttribute("successMessage", "Pagamento adicionado");
        return "redirect:/serviceExecution/" + serviceId + "/checkout";

    }

    @PostMapping("/finish")
    public String finishServiceExecution(@PathVariable Long serviceId, Model model, RedirectAttributes attributes) {

        service.finish(serviceId);

        attributes.addFlashAttribute("successMessage", "Atendimento concluído");
        return "redirect:/serviceExecution";

    }

    @PostMapping("/payment/{id}/delete")
    public String removePayment(@PathVariable Long serviceId, @PathVariable Long id, RedirectAttributes attributes) {

        ServiceExecution s = service.findById(serviceId);
        Payment p = paymentService.findById(id);

        service.removePayment(s, p);
        attributes.addFlashAttribute("errorMessage", "Pagamento removido");
        return "redirect:/serviceExecution/" + serviceId + "/checkout";

    }

    @PostMapping("/payment/{id}/update")
    public String updatePayment(@PathVariable Long serviceId,
            @PathVariable Long id,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) Long methodId,
            @RequestParam(required = false) String obs, RedirectAttributes attributes) {

        ServiceExecution s = service.findById(serviceId);
        Payment p = paymentService.findById(id);

        if (p.getStatus() == PaymentStatus.TEMPORARY) {

            if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
                p.updateAmount(amount);
            }

            if (methodId != null) {
                PaymentMethod method = paymentService.findPaymentMethodById(methodId);
                p.updatePaymentMethod(method);
            }

            p.updateObservation(obs);

            paymentService.save(p);
            service.save(s);

        }

        attributes.addFlashAttribute("successMessage", "Pagamento atualizado");
        return "redirect:/serviceExecution/" + serviceId + "/checkout";

    }

}
