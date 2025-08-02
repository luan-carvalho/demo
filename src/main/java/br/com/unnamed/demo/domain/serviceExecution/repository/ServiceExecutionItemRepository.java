package br.com.unnamed.demo.domain.serviceExecution.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecutionItem;

public interface ServiceExecutionItemRepository extends JpaRepository<ServiceExecutionItem, Long> {
    
}
