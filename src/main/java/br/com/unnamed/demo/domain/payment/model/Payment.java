package br.com.unnamed.demo.domain.payment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id")
    @NotNull
    private PaymentMethod paymentMethod;

    @Column(precision = 10, scale = 2)
    @NotNull
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "service_execution_id")
    @NotNull
    private ServiceExecution serviceExecution;

    private String observation;

    public Payment() {
    }

    public Payment(LocalDate date, PaymentMethod paymentMethod, BigDecimal amount,
            ServiceExecution serviceExecution, String observation) {

        this.date = date;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.serviceExecution = serviceExecution;
        this.observation = observation;

    }

}
