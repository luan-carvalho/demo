package br.com.unnamed.demo.domain.report.model;

import java.math.BigDecimal;
import java.util.List;

import br.com.unnamed.demo.domain.payment.dto.PaymentReportDto;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.report.model.valueObject.PaymentMethodGroup;
import lombok.Getter;

@Getter
public class PaymentsReport {

    private List<PaymentMethodGroup> payments;

    public PaymentsReport addMethodWithPayments(PaymentMethod method, List<PaymentReportDto> paymentsGrouped) {

        payments.add(new PaymentMethodGroup(method, paymentsGrouped));
        return this;

    }

    public BigDecimal calculateTotal() {

        return payments.stream().map(PaymentMethodGroup::calculateTotal).reduce(BigDecimal.ZERO,
                BigDecimal::add);

    }

}
