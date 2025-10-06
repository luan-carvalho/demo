package br.com.unnamed.demo.domain.report.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import br.com.unnamed.demo.domain.report.model.PaymentsReport;
import br.com.unnamed.demo.domain.report.model.valueObject.PaymentMethodReport;
import br.com.unnamed.demo.domain.report.service.PaymentReportService;
import br.com.unnamed.demo.domain.report.strategy.CurrentMonthPaymentsReport;
import br.com.unnamed.demo.domain.report.strategy.LastMonthPaymentsReport;
import br.com.unnamed.demo.domain.report.strategy.PaymentReportPeriod;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("report")
public class ReportController {

    private final PaymentReportService paymentReportService;
    private final TemplateEngine templateEngine;

    public ReportController(PaymentReportService paymentReportService, TemplateEngine templateEngine) {
        this.paymentReportService = paymentReportService;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/payments")
    public String showPaymentsReport(Model model, @RequestParam(required = false) String period) {

        PaymentReportPeriod reportPeriod = switch (period) {

            case null -> new CurrentMonthPaymentsReport();
            case "thisMonth" -> new CurrentMonthPaymentsReport();
            case "lastMonth" -> new LastMonthPaymentsReport();
            default -> new CurrentMonthPaymentsReport();

        };

        PaymentsReport report = paymentReportService.create(reportPeriod);

        List<String> paymentLabels = report.getPaymentMethods().stream()
                .map(pm -> pm.getMethod().getDescription())
                .toList();

        List<BigDecimal> paymentTotals = report.getPaymentMethods().stream()
                .map(PaymentMethodReport::getTotal)
                .toList();

        model.addAttribute("paymentLabels", paymentLabels);
        model.addAttribute("paymentTotals", paymentTotals);

        model.addAttribute("report", report);
        model.addAttribute("period", period == null ? "thisMonth" : period);
        model.addAttribute("view", "report/payments-report");
        model.addAttribute("activePage", "payments-report");
        model.addAttribute("pageTitle", "Relatório | Recebimentos");
        return "layout/base-layout";

    }

    @GetMapping("/payments/export/pdf")
    public void exportPdf(HttpServletResponse response, @RequestParam(required = false) String period)
            throws Exception {

        PaymentReportPeriod reportPeriod = switch (period) {

            case null -> new CurrentMonthPaymentsReport();
            case "thisMonth" -> new CurrentMonthPaymentsReport();
            case "lastMonth" -> new LastMonthPaymentsReport();
            default -> new CurrentMonthPaymentsReport();

        };

        PaymentsReport report = paymentReportService.create(reportPeriod);

        response.setContentType("application/pdf");
        String filename = String.format("relatorio-pagamentos-%s-a-%s.pdf",
                reportPeriod.getBeginInclusiveDate(), reportPeriod.getEndExclusiveDate().minusDays(1));
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + filename + "\"");

        Context context = new Context(Locale.getDefault());
        context.setVariable("report", report);

        String htmlContent = templateEngine.process("report/payments-report-pdf", context);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(response.getOutputStream());
    }

}
