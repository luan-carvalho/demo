package br.com.unnamed.demo.domain.serviceExecution.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;

public interface ServiceExecutionRepository extends JpaRepository<ServiceExecution, Long> {

    @Query("SELECT s FROM ServiceExecution s WHERE s.serviceStatus = :serviceStatus AND s.date = :date")
    public List<ServiceExecution> findByStatusAndDate(ServiceStatus serviceStatus, LocalDate date);

}
