package br.com.unnamed.demo.domain.report.model.valueObject;

import lombok.Getter;

@Getter
public class WeekDayReport {

    private String dayString;
    private Double average;

    public WeekDayReport(String dayString, Double average) {
        this.dayString = dayString;
        this.average = average;
    }

}
