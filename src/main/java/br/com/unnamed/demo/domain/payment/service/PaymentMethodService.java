package br.com.unnamed.demo.domain.payment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.unnamed.demo.domain.payment.model.PaymentMethod;
import br.com.unnamed.demo.domain.payment.model.enums.PaymentMethodType;
import br.com.unnamed.demo.domain.payment.repository.PaymentMethodRepository;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository repo;

    public PaymentMethodService(PaymentMethodRepository repo) {
        this.repo = repo;
    }

    public List<PaymentMethod> findAllActive() {

        return repo.findAllActive();

    }

    public PaymentMethod findById(Long id) {

        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Payment method not found"));

    }

    public void save(PaymentMethod paymentMethod) {

        repo.save(paymentMethod);

    }

    public void deactivate(Long id) {

        PaymentMethod toBeDeactivated = findById(id);
        toBeDeactivated.deactivate();
        save(toBeDeactivated);

    }

    public void activate(Long id) {

        PaymentMethod toBeActivated = findById(id);
        toBeActivated.activate();
        save(toBeActivated);

    }

    public Page<PaymentMethod> searchWithOptionalFilters(String description,  Status status,
            Pageable pageable) {

        return repo.searchWithOptionalFilters(description, status, pageable);

    }

}
