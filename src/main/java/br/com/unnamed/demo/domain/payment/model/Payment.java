package br.com.unnamed.demo.domain.payment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.unnamed.demo.domain.checkout.model.Checkout;
import br.com.unnamed.demo.domain.payment.model.enums.PaymentStatus;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkout_id", nullable = false)
    private Checkout checkout;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    public Payment(PaymentMethod paymentMethod,
            BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Não é possível criar um pagamento com valor negativo ou 0");

        this.date = LocalDate.now();
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = PaymentStatus.TEMPORARY;

    }

    public void markAsFinal() {

        this.status = PaymentStatus.FINAL;

    }

    public void markAsCancelled() {

        this.status = PaymentStatus.CANCELLED;

    }

    public void updateAmount(BigDecimal amount) {

        this.amount = amount;

    }

    public void updatePaymentMethod(PaymentMethod paymentMethod) {

        this.paymentMethod = paymentMethod;

    }

}
