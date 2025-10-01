package br.com.unnamed.demo.domain.report.strategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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

    @Override
    public String getPeriodString() {

        return getBeginInclusiveDate().format(DateTimeFormatter.ofPattern("dd/MM/yy")) + " - "
                + getEndExclusiveDate().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yy"));

    }

    @Override
    public long getNumberOfDays() {

        return ChronoUnit.DAYS.between(getBeginInclusiveDate(), getEndExclusiveDate());

    }

}
