package br.com.unnamed.demo.domain.report.strategy;

import java.time.LocalDate;

public class LastMonthPaymentsReport implements PaymentReportPeriod {

    private static final LocalDate now = LocalDate.now();

    @Override
    public LocalDate getBeginInclusiveDate() {

        LocalDate lastMonthDate = now.minusMonths(1);

        return LocalDate.of(lastMonthDate.getYear(), lastMonthDate.getMonthValue(), 1);

    }

    @Override
    public LocalDate getEndExclusiveDate() {

        return getBeginInclusiveDate().plusMonths(1);

    }

}
