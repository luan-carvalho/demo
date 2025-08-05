package br.com.unnamed.demo.domain.payment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentType;
import br.com.unnamed.demo.domain.payment.repository.PaymentTypeRepository;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Service
public class PaymentTypeService {

    private final PaymentTypeRepository repo;

    public PaymentTypeService(PaymentTypeRepository repo) {
        this.repo = repo;
    }

    public List<PaymentType> findAllActive() {

        return repo.findAllActive();

    }

    public PaymentType findById(Long id) {

        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet care group not found"));

    }

    public void save(PaymentType paymentType) {

        repo.save(paymentType);

    }

    public void deactivate(Long id) {

        PaymentType toBeDeactivated = findById(id);
        toBeDeactivated.deactivate();
        save(toBeDeactivated);

    }

    public void activate(Long id) {

        PaymentType toBeActivated = findById(id);
        toBeActivated.activate();
        save(toBeActivated);

    }

    public Page<PaymentType> searchWithOptionalFilters(String description, Status status, Pageable pageable) {

        return repo.searchWithOptionalFilters(description, status, pageable);

    }

}
