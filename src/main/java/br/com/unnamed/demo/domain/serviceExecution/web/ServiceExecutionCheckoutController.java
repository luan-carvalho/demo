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
@RequestMapping("serviceExecution/{serviceId}/checkout")
public class ServiceExecutionCheckoutController {

    private final ServiceExecutionCheckoutFacade facade;

    public ServiceExecutionCheckoutController(ServiceExecutionCheckoutFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String checkout(@PathVariable Long serviceId, Model model) {

        model.addAttribute("serviceExecution",
                new ServiceExecutionCheckoutDto(facade.findServiceExecutionById(serviceId)));
        model.addAttribute("all_payment_types",
                facade.getAllPaymentMethods());
        model.addAttribute("activePage", "serviceExecution");
        model.addAttribute("view", "serviceExecution/serviceExecutionCheckout");
        model.addAttribute("pageTitle", "Pagamento | Atendimento #" + serviceId);

        return "layout/base-layout";

    }

    @PostMapping("/addPayment")
    public String addPaymentToServiceExecution(
            @PathVariable Long serviceId,
            Long typeId,
            BigDecimal amount,
            @RequestParam(required = false) Integer installments,
            RedirectAttributes attributes) {

        facade.addPayment(serviceId, typeId, amount, installments);
        attributes.addFlashAttribute("successMessage", "Pagamento adicionado");
        return "redirect:/serviceExecution/" + serviceId + "/checkout";

    }

    @PostMapping("/finish")
    public String finishServiceExecution(@PathVariable Long serviceId, Model model, RedirectAttributes attributes) {

        facade.finishServiceExecution(serviceId);

        attributes.addFlashAttribute("successMessage", "Atendimento concluído");
        return "redirect:/serviceExecution";

    }

    @PostMapping("/payment/{paymentId}/delete")
    public String removePayment(@PathVariable Long serviceId, @PathVariable Long paymentId,
            RedirectAttributes attributes) {

        facade.removePayment(serviceId, paymentId);
        attributes.addFlashAttribute("errorMessage", "Pagamento removido");
        return "redirect:/serviceExecution/" + serviceId + "/checkout";

    }

    @PostMapping("/payment/{paymentId}/update")
    public String updatePayment(@PathVariable Long serviceId,
            @PathVariable Long paymentId,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) Long methodId,
            RedirectAttributes attributes) {

        facade.updatePaymentInfo(serviceId, paymentId, methodId, amount);

        attributes.addFlashAttribute("successMessage", "Pagamento atualizado");
        return "redirect:/serviceExecution/" + serviceId + "/checkout";

    }

}
