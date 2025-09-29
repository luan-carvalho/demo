package br.com.unnamed.demo.domain.report.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.unnamed.demo.domain.report.service.PaymentReportService;
import br.com.unnamed.demo.domain.report.strategy.CurrentMonthPaymentsReport;
import br.com.unnamed.demo.domain.report.strategy.LastMonthPaymentsReport;
import br.com.unnamed.demo.domain.report.strategy.PaymentReportPeriod;

@Controller
@RequestMapping("report")
public class ReportController {

    private final PaymentReportService paymentReportService;

    public ReportController(PaymentReportService paymentReportService) {
        this.paymentReportService = paymentReportService;
    }

    @GetMapping("/payments")
    public String showPaymentsReport(Model model, @RequestParam(required = false) String period) {

        PaymentReportPeriod reportPeriod = switch (period) {
            case "current" -> new CurrentMonthPaymentsReport();
            case "last" -> new LastMonthPaymentsReport();
            default -> new CurrentMonthPaymentsReport();

        };

        

        model.addAttribute("view", "report/payments-report");
        model.addAttribute("activePage", "reports");
        model.addAttribute("pageTitle", "Relatório | Recebimentos");
        return "layout/base-layout";

    }

}
