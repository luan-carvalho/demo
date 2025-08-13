package br.com.unnamed.demo.domain.payment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @NotNull
    private String tutorName;

    @NotNull
    private String petName;

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

    public Payment(@NotNull LocalDate date, String tutorName, String petName, @NotNull PaymentMethod paymentMethod,
            @NotNull BigDecimal amount, String observation) {
        this.date = date;
        this.tutorName = tutorName;
        this.petName = petName;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.observation = observation;
    }

}
