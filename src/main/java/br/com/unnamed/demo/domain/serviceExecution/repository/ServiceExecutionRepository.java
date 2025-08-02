package br.com.unnamed.demo.domain.serviceExecution.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;

public interface ServiceExecutionRepository extends JpaRepository<ServiceExecution, Long> {
    
}
