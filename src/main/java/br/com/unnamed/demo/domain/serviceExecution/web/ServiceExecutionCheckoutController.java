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

import br.com.unnamed.demo.domain.serviceExecution.dto.ServiceExecutionCheckoutDto;
import br.com.unnamed.demo.domain.serviceExecution.facade.ServiceExecutionCheckoutFacade;

@Controller
@RequestMapping("serviceExecution/{serviceExecutionId}/checkout")
public class ServiceExecutionCheckoutController {

    private final ServiceExecutionCheckoutFacade facade;

    public ServiceExecutionCheckoutController(ServiceExecutionCheckoutFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String checkout(@PathVariable Long serviceExecutionId, Model model) {

        model.addAttribute("serviceExecution",
                new ServiceExecutionCheckoutDto(facade.findServiceExecutionById(serviceExecutionId)));
        model.addAttribute("all_payment_types",
                facade.getAllPaymentMethods());
        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecutionCheckout");
        model.addAttribute("pageTitle", "Pagamento | Atendimento #" + serviceExecutionId);

        return "layout/base-layout";

    }

    @PostMapping("/addPayment")
    public String addPaymentToServiceExecution(
            @PathVariable Long serviceExecutionId,
            Long typeId,
            BigDecimal amount,
            @RequestParam(required = false) Integer installments,
            RedirectAttributes attributes) {

        facade.addPayment(serviceExecutionId, typeId, amount, installments);
        attributes.addFlashAttribute("successMessage", "Pagamento adicionado");
        return "redirect:/serviceExecution/" + serviceExecutionId + "/checkout";

    }

    @PostMapping("/finish")
    public String finishServiceExecution(@PathVariable Long serviceExecutionId, Model model,
            RedirectAttributes attributes) {

        facade.finishServiceExecution(serviceExecutionId);

        attributes.addFlashAttribute("successMessage", "Atendimento concluído");
        return "redirect:/serviceExecution";

    }

    @PostMapping("/payment/{paymentId}/delete")
    public String removePayment(@PathVariable Long serviceExecutionId, @PathVariable Long paymentId,
            RedirectAttributes attributes) {

        facade.removePayment(serviceExecutionId, paymentId);
        attributes.addFlashAttribute("errorMessage", "Pagamento removido");
        return "redirect:/serviceExecution/" + serviceExecutionId + "/checkout";

    }

    @PostMapping("/payment/{paymentId}/update")
    public String updatePayment(@PathVariable Long serviceExecutionId,
            @PathVariable Long paymentId,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) Long methodId,
            RedirectAttributes attributes) {

        facade.updatePaymentInfo(serviceExecutionId, paymentId, methodId, amount);

        attributes.addFlashAttribute("successMessage", "Pagamento atualizado");
        return "redirect:/serviceExecution/" + serviceExecutionId + "/checkout";

    }

    @PostMapping("/reopen")
    public String reopen(@PathVariable Long serviceExecutionId, RedirectAttributes attributes) {

        facade.reopenServiceExecution(serviceExecutionId);
        attributes.addFlashAttribute("successMessage", "Atendimento reaberto!");

        return "redirect:/serviceExecution/" + serviceExecutionId + "/checkout";

    }

}
