package br.com.unnamed.demo.domain.report.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.unnamed.demo.domain.report.model.PaymentsReport;
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

        PaymentReportPeriod reportPeriod = new CurrentMonthPaymentsReport();

        if (period != null && period.equals("last")) {

            reportPeriod = new LastMonthPaymentsReport();

        }

        PaymentsReport report = paymentReportService.create(reportPeriod);

        model.addAttribute("report", report);
        model.addAttribute("view", "report/payments-report");
        model.addAttribute("activePage", "payments-report");
        model.addAttribute("pageTitle", "Relatório | Recebimentos");
        return "layout/base-layout";

    }

}
