package br.com.unnamed.demo.domain.payment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unnamed.demo.domain.payment.model.PaymentMethod;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

        @Query("SELECT p FROM PaymentMethod p WHERE p.status = 'ACTIVE'")
        List<PaymentMethod> findAllActive();

        @Query("""
                        SELECT DISTINCT p FROM PaymentMethod p
                        WHERE (:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', CAST(:description AS STRING), '%')))
                        AND (:status IS NULL OR p.status = :status)
                        """)
        Page<PaymentMethod> searchWithOptionalFilters(String description, Status status, Pageable pageable);

}
