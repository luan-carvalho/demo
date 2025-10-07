package br.com.unnamed.demo.domain.serviceExecution.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;

public interface ServiceExecutionRepository extends JpaRepository<ServiceExecution, Long> {

    @Query("""
            SELECT s
            FROM ServiceExecution s
            WHERE s.serviceStatus = :serviceStatus
            AND s.date = :date
            AND s.paymentStatus = 'NOT_PAID'
            """)
    public List<ServiceExecution> findByStatusAndDate(ServiceStatus serviceStatus, LocalDate date);

    @Query("""
            SELECT s
            FROM ServiceExecution s
            WHERE s.paymentStatus = 'NOT_PAID'
            AND s.date < CURRENT_DATE
            """)
    public List<ServiceExecution> existsWithPendingPayment();

    List<ServiceExecution> findTop10ByPetIdOrderByDateDesc(Long petId);

    @Query("""
            SELECT s
            FROM ServiceExecution s
            WHERE (CAST(:name AS STRING) IS NULL
                   OR LOWER(s.tutor.info.name) LIKE LOWER(CONCAT('%', CAST(:name AS STRING), '%'))
                   OR LOWER(s.pet.name) LIKE LOWER(CONCAT('%', CAST(:name AS STRING), '%'))
                  )
              AND (COALESCE(:date, s.date) = s.date)
              AND (:status IS NULL OR s.serviceStatus = :status)
              AND (:paymentStatus IS NULL OR s.paymentStatus = :paymentStatus)
            """)

    Page<ServiceExecution> findWithOptionalFilters(String name, LocalDate date, ServiceStatus status,
            ServicePaymentStatus paymentStatus,
            Pageable pageable);


}
