package br.com.unnamed.demo.domain.report.strategy;

import java.time.LocalDate;

public interface PaymentReportPeriod {

    public LocalDate getBeginInclusiveDate();

    public LocalDate getEndExclusiveDate();

    public String getPeriodString();

}
