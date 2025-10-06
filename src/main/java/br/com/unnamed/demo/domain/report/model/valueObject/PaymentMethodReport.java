package br.com.unnamed.demo.domain.report.model.valueObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import lombok.Getter;

@Getter
public class PaymentMethodReport {

    private PaymentMethod method;
    private Long count;
    private BigDecimal total;
    private Double percentage;

    public PaymentMethodReport(PaymentMethod method, Long count, BigDecimal total) {
        this.method = method;
        this.count = count;
        this.total = total;
    }

    public void setPercentage(BigDecimal allMethodsTotal) {

        this.percentage = this.total.divide(allMethodsTotal, RoundingMode.UP).doubleValue() * 100.00;

    }

}
