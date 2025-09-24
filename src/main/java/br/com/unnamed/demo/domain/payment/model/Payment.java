package br.com.unnamed.demo.domain.payment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.unnamed.demo.domain.payment.model.enums.PaymentStatus;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
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
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_execution_id", nullable = false)
    private ServiceExecution serviceExecution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id")
    @NotNull
    private PaymentMethod paymentMethod;

    @Column(precision = 10, scale = 2)
    @NotNull
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String observation;

    public void linkToServiceExecution(ServiceExecution s) {

        this.serviceExecution = s;

    }

    public void updateStatus(PaymentStatus status) {

        this.status = status;

    }

    public void updateAmount(BigDecimal amount) {

        this.amount = amount;

    }

    public void updatePaymentMethod(PaymentMethod paymentMethod) {

        this.paymentMethod = paymentMethod;

    }

    public void updateObservation(String observation) {

        this.observation = observation;

    }

}
