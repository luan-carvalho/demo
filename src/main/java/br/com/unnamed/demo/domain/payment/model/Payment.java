package br.com.unnamed.demo.domain.payment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
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

    @ManyToOne
    @JoinColumn(name = "service_execution_id")
    private ServiceExecution serviceExecution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id")
    @NotNull
    private PaymentMethod paymentMethod;

    @Column(precision = 10, scale = 2)
    @NotNull
    private BigDecimal amount;

    private String observation;

    public Payment() {
    }

    public Payment(@NotNull LocalDate date, @NotNull ServiceExecution serviceExecution,
            @NotNull PaymentMethod paymentMethod, @NotNull BigDecimal amount, String observation) {
        this.date = date;
        this.serviceExecution = serviceExecution;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.observation = observation;
    }

}
