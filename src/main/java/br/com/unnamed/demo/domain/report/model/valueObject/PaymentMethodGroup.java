package br.com.unnamed.demo.domain.report.model.valueObject;

import java.math.BigDecimal;
import java.util.List;

import br.com.unnamed.demo.domain.payment.dto.PaymentReportDto;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import lombok.Getter;

@Getter
public class PaymentMethodGroup {

    private PaymentMethod method;
    private List<PaymentReportDto> payments;

    public PaymentMethodGroup(PaymentMethod method, List<PaymentReportDto> payments) {
        this.method = method;
        this.payments = payments;
    }

    public BigDecimal calculateTotal() {

        return payments.stream().map(PaymentReportDto::amount).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

}
