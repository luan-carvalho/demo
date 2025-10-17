package br.com.unnamed.demo.domain.report.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.payment.dto.PaymentReportDto;
import br.com.unnamed.demo.domain.payment.repository.PaymentMethodRepository;
import br.com.unnamed.demo.domain.payment.repository.PaymentRepository;
import br.com.unnamed.demo.domain.report.model.PaymentsReport;
import br.com.unnamed.demo.domain.report.model.valueObject.PaymentMethodReport;
import br.com.unnamed.demo.domain.report.strategy.PaymentReportPeriod;

@Service
public class PaymentReportService {

    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentReportService(PaymentRepository paymentRepository, PaymentMethodRepository paymentMethodRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    // public PaymentsReport create(PaymentReportPeriod period) {

    // LocalDate beginInclusive = period.getBeginInclusiveDate();
    // LocalDate endExclusive = period.getEndExclusiveDate();
    // long numberOfDays = period.getNumberOfDays();
    // List<PaymentReportDto> payments =
    // paymentRepository.findBetweenPeriodConvertingToDto(beginInclusive,
    // endExclusive);
    // List<PaymentMethodReport> paymentMethodReport = paymentMethodRepository
    // .generatePaymentMethodSummary(beginInclusive, endExclusive);
    // String periodString = period.getPeriodString();

    // PaymentsReport report = new PaymentsReport(periodString,
    // numberOfDays,
    // payments,
    // paymentMethodReport);

    // return report;

    // }

}
