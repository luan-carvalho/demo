package br.com.unnamed.demo.domain.report.strategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CurrentMonthPaymentsReport implements PaymentReportPeriod {

    private static final LocalDate now = LocalDate.now();

    @Override
    public LocalDate getBeginInclusiveDate() {

        return LocalDate.of(now.getYear(), now.getMonthValue(), 1);

    }

    @Override
    public LocalDate getEndExclusiveDate() {

        return now.plusDays(1);

    }

    @Override
    public String getPeriodString() {
        return getBeginInclusiveDate().format(DateTimeFormatter.ofPattern("dd/MM/yy")) + " - "
                + getEndExclusiveDate().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
    }

    @Override
    public long getNumberOfDays() {

        return ChronoUnit.DAYS.between(getBeginInclusiveDate(), getEndExclusiveDate());

    }

}
