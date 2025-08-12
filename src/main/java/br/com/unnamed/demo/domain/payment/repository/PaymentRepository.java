package br.com.unnamed.demo.domain.payment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentType;

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
                    AND (CAST(:date AS DATE) IS NULL OR p.date = CAST(:date AS DATE))
                    AND (:type IS NULL OR p.type = :type)
                    AND p.status = 'ACTIVE'
                    ORDER BY p.date DESC
            """)
    List<Payment> searchWithOptionalFilters(String name, @Param("type") PaymentType type, @Param("date") LocalDate date);

}
