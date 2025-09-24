package br.com.unnamed.demo.domain.payment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("""
                SELECT DISTINCT p
                FROM Payment p
                WHERE
                    (
                        :name IS NULL
                        OR LOWER(p.serviceExecution.tutor.info.name) LIKE LOWER(CONCAT('%', CAST(:name AS STRING), '%'))
                        OR LOWER(p.serviceExecution.pet.name) LIKE LOWER(CONCAT('%', CAST(:name AS STRING), '%'))
                    )
                    AND (COALESCE(:date, p.date) = p.date)
                    AND (:paymentMethod IS NULL OR p.paymentMethod = :paymentMethod)
                    ORDER BY p.date DESC
            """)
    List<Payment> searchWithOptionalFilters(String name, PaymentMethod paymentMethod, LocalDate date);

}
