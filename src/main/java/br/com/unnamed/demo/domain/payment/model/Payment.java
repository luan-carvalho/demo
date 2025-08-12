package br.com.unnamed.demo.domain.payment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.unnamed.demo.domain.payment.model.enums.PaymentStatus;
import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentType;
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
import lombok.Getter;

@Entity
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    // @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id")
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(precision = 10, scale = 2)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "service_execution_id")
    private ServiceExecution serviceExecution;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Payment() {
    }

    public Payment(@NotNull LocalDate date, @NotNull PaymentType type, BigDecimal value,
            ServiceExecution serviceExecution, PaymentStatus status) {
        this.date = date;
        this.type = type;
        this.value = value;
        this.serviceExecution = serviceExecution;
        this.status = status;
    }

    public void delete() {

        this.status = PaymentStatus.DELETED;

    }

    public void updateAmountAndType(BigDecimal value, PaymentType type) {

        this.value = value;
        this.type = type;

    }

}
