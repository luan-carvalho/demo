package br.com.unnamed.demo.domain.payment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.payment.model.PaymentMethod;

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
                    AND (CAST(:date AS DATE) IS NULL OR p.date = CAST(:date AS DATE))
                    AND (:paymentMethod IS NULL OR p.paymentMethod = :paymentMethod)
                    ORDER BY p.date DESC
            """)
    List<Payment> searchWithOptionalFilters(String name, @Param("type") PaymentMethod paymentMethod,
            @Param("date") LocalDate date);

}
