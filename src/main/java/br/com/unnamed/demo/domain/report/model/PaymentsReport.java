package br.com.unnamed.demo.domain.report.model;

import java.math.BigDecimal;
import java.util.List;

import br.com.unnamed.demo.domain.payment.dto.PaymentReportDto;
import br.com.unnamed.demo.domain.report.model.valueObject.PaymentMethodReport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentsReport {

    private String periodString;
    private List<PaymentReportDto> payments;
    private List<PaymentMethodReport> paymentMethods;
    private BigDecimal total;
    private int paymentsCount;

    public PaymentsReport(String periodString, List<PaymentReportDto> payments,
            List<PaymentMethodReport> paymentMethods) {

        this.periodString = periodString;
        this.payments = payments;
        this.paymentMethods = paymentMethods;
        this.total = payments
                .stream()
                .map(PaymentReportDto::amount)
                .reduce(BigDecimal.ZERO,
                        BigDecimal::add);
        this.paymentsCount = payments.size();

    }

}
