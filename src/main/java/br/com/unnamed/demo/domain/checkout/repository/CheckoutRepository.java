package br.com.unnamed.demo.domain.checkout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.unnamed.demo.domain.checkout.model.Checkout;
import br.com.unnamed.demo.domain.tutor.model.Tutor;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByTutor(Tutor tutor);

}
