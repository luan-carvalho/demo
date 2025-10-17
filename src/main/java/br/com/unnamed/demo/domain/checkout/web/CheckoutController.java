package br.com.unnamed.demo.domain.checkout.web;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.unnamed.demo.domain.checkout.facade.CheckoutFacade;
import br.com.unnamed.demo.domain.checkout.model.Checkout;

@Controller
@RequestMapping("checkout/{checkoutId}")
public class CheckoutController {

    private final CheckoutFacade facade;

    public CheckoutController(CheckoutFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String openCheckout(@PathVariable Long checkoutId, Model model) {

        Checkout c = facade.findById(checkoutId);

        model.addAttribute("checkout", c);
        model.addAttribute("all_payment_types", facade.getAllPaymentMethods());
        model.addAttribute("view", "checkout/checkout");
        model.addAttribute("activePage", "clients");
        model.addAttribute("pageTitle", "Clientes");
        return "layout/base-layout";

    }

    @PostMapping("/addPayment")
    public String addPaymentToServiceExecution(Long checkoutId, Long paymentMethodId, BigDecimal amount,
            @RequestParam(required = false) Integer installments,
            RedirectAttributes attributes) {

        facade.addPayment(checkoutId, paymentMethodId, amount, installments);
        attributes.addFlashAttribute("successMessage", "Pagamento adicionado");
        return "redirect:/checkout" + checkoutId;

    }

    // @PostMapping("/payment/{id}/delete")
    // public String removePayment(@PathVariable Long serviceId, @PathVariable Long
    // id, RedirectAttributes attributes) {

    // ServiceExecution s = service.findById(serviceId);
    // Payment p = paymentService.findById(id);

    // service.removePayment(s, p);
    // attributes.addFlashAttribute("errorMessage", "Pagamento removido");
    // return "redirect:/serviceExecution/" + serviceId + "/checkout";

    // }

    // @PostMapping("/payment/{id}/update")
    // public String updatePayment(@PathVariable Long serviceId,
    // @PathVariable Long id,
    // @RequestParam(required = false) BigDecimal amount,
    // @RequestParam(required = false) Long methodId,
    // @RequestParam(required = false) String obs, RedirectAttributes attributes) {

    // ServiceExecution s = service.findById(serviceId);
    // Payment p = paymentService.findById(id);

    // if (p.getStatus() == PaymentStatus.TEMPORARY) {

    // if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
    // p.updateAmount(amount);
    // }

    // if (methodId != null) {
    // PaymentMethod method = paymentService.findPaymentMethodById(methodId);
    // p.updatePaymentMethod(method);
    // }

    // p.updateObservation(obs);

    // paymentService.save(p);
    // service.save(s);

    // }

    // attributes.addFlashAttribute("successMessage", "Pagamento atualizado");
    // return "redirect:/serviceExecution/" + serviceId + "/checkout";

    // }

}
