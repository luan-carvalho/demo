package br.com.unnamed.demo.domain.payment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unnamed.demo.domain.payment.dto.PaymentReportDto;
import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("""
                SELECT DISTINCT p
                FROM Payment p
                WHERE
                    (
                        :name IS NULL
                        OR LOWER(p.tutorName) LIKE LOWER(CONCAT('%', CAST(:name AS STRING), '%'))
                        OR LOWER(p.petName) LIKE LOWER(CONCAT('%', CAST(:name AS STRING), '%'))
                    )
                    AND (COALESCE(:date, p.date) = p.date)
                    AND (:paymentMethod IS NULL OR p.paymentMethod = :paymentMethod)
                    AND p.status = 'FINAL'
                    ORDER BY p.date DESC
            """)
    List<Payment> searchWithOptionalFilters(String name, PaymentMethod paymentMethod, LocalDate date);

    @Query("""
            SELECT new br.com.unnamed.demo.domain.payment.dto.PaymentReportDto(
            p.amount,
            p.serviceExecution.tutor.name,
            p.serviceExecution.pet.name,
            p.date,
            p.serviceExecution.id,
            p.paymentMethod.description)
            FROM Payment p
            WHERE p.date >= :beginInclusive
            AND p.date < :endExclusive
            AND p.status = 'FINAL'
            ORDER BY p.date
            """)
    public List<PaymentReportDto> findBetweenPeriodConvertingToDto(LocalDate beginInclusive, LocalDate endExclusive);

}
