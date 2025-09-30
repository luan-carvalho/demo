package br.com.unnamed.demo.domain.payment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.report.model.valueObject.PaymentMethodReport;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

        @Query("""
                        SELECT new br.com.unnamed.demo.domain.report.model.valueObject.PaymentMethodReport(
                                p.paymentMethod,
                                COUNT(p),
                                SUM(p.amount))
                        FROM Payment p
                        WHERE p.date >= :beginInclusive
                        AND p.date < :endExclusive
                        AND p.status = 'FINAL'
                        GROUP BY p.paymentMethod
                        ORDER BY SUM(p.amount) DESC
                        """)
        List<PaymentMethodReport> generatePaymentMethodSummary(LocalDate beginInclusive, LocalDate endExclusive);

}
