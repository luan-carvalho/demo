package br.com.unnamed.demo.domain.report.model;

import java.math.BigDecimal;
import java.util.List;

import br.com.unnamed.demo.domain.payment.dto.PaymentReportDto;
import br.com.unnamed.demo.domain.report.model.valueObject.PaymentMethodReport;
import br.com.unnamed.demo.domain.report.model.valueObject.WeekDayReport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentsReport {

    private String periodString;
    private long numberOfDays;
    private List<PaymentReportDto> payments;
    private List<PaymentMethodReport> paymentMethods;
    private List<WeekDayReport> weekDayReport;
    private BigDecimal total;
    private BigDecimal averageTicket;
    private BigDecimal averageDailyRevenue;
    private int paymentsCount;

    public PaymentsReport(String periodString,
            long numberOfDays,
            List<PaymentReportDto> payments,
            List<WeekDayReport> weekDayReport,
            List<PaymentMethodReport> paymentMethods) {

        this.numberOfDays = numberOfDays;
        this.periodString = periodString;
        this.payments = payments;
        this.paymentMethods = paymentMethods;
        this.weekDayReport = weekDayReport;
        this.total = payments
                .stream()
                .map(PaymentReportDto::amount)
                .reduce(BigDecimal.ZERO,
                        BigDecimal::add);
        this.paymentMethods.forEach(pm -> pm.setPercentage(total));
        this.paymentsCount = payments.size();
        this.averageTicket = paymentsCount == 0 ? BigDecimal.ZERO : total.divide(BigDecimal.valueOf(paymentsCount));
        this.averageDailyRevenue = paymentsCount == 0 ? BigDecimal.ZERO
                : total.divide(BigDecimal.valueOf(numberOfDays));

    }
}