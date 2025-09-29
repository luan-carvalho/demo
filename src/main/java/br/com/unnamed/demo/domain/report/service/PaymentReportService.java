package br.com.unnamed.demo.domain.report.service;

import java.time.LocalDate;

import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.payment.repository.PaymentMethodRepository;
import br.com.unnamed.demo.domain.payment.repository.PaymentRepository;
import br.com.unnamed.demo.domain.report.model.PaymentsReport;
import br.com.unnamed.demo.domain.report.strategy.PaymentReportPeriod;

public class PaymentReportService {

    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentReportService(PaymentRepository paymentRepository, PaymentMethodRepository paymentMethodRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentsReport create(PaymentReportPeriod period) {

        LocalDate beginInclusive = period.getBeginInclusiveDate();
        LocalDate endExclusive = period.getEndExclusiveDate();

        PaymentsReport report = new PaymentsReport();

        for (PaymentMethod method : paymentMethodRepository.findAll()) {

            report.addMethodWithPayments(method,
                    paymentRepository.findByDateAndMethod(beginInclusive, endExclusive, method));
        }

        return report;

    }

}
