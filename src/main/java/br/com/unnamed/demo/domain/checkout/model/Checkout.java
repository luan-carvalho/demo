package br.com.unnamed.demo.domain.checkout.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.checkout.model.enums.CheckoutStatus;
import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.petCarePackage.model.PetCarePackageClient;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private CheckoutStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "checkout")
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckoutItem> items = new ArrayList<>();

    public Checkout(Tutor tutor) {

        this.tutor = tutor;
        this.creationDate = LocalDate.now();
        this.status = CheckoutStatus.PENDING;

    }

    public void addItem(ServiceExecution serviceExecution) {

        this.items.add(new CheckoutItem(this, serviceExecution));

    }

    public void addItem(PetCarePackageClient petCarePackageClient) {

        this.items.add(new CheckoutItem(this, petCarePackageClient));

    }

    public BigDecimal total() {

        return this.items.stream().map(CheckoutItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public BigDecimal paid() {

        return this.payments.stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public BigDecimal balance() {

        return total().subtract(paid());

    }

    public void addPayment(Payment payment) {

        if (payment.getAmount().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Não é possível adicionar um pagamento com valor negativo");

        if (payment.getAmount().compareTo(total()) > 0)
            throw new IllegalArgumentException(
                    "Não é possível adicionar um pagamento maior que o valor total do atendimento");

        if (balance().compareTo(payment.getAmount()) < 0)
            throw new IllegalArgumentException(
                    "Não é possível adicionar este pagamento, pois o serviço já foi totalmente pago");

        this.payments.add(payment);

    }

    public void addPayments(List<Payment> payments) {

        payments.forEach(p -> addPayment(p));

    }

}
