package br.com.unnamed.demo.domain.checkout.model;

import java.math.BigDecimal;

import br.com.unnamed.demo.domain.petCarePackage.model.PetCarePackageClient;
import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CheckoutItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Checkout checkout;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "service_execution_id")
    private ServiceExecution serviceExecution;

    @OneToOne
    @JoinColumn(name = "package_client_id")
    private PetCarePackageClient packageClient;

    public CheckoutItem(Checkout checkout, ServiceExecution serviceExecution) {

        this.checkout = checkout;
        this.serviceExecution = serviceExecution;
        this.amount = serviceExecution.calculateTotal();
        this.description = serviceExecution.toString();

    }

    public CheckoutItem(Checkout checkout, PetCarePackageClient packageClient) {

        this.checkout = checkout;
        this.packageClient = packageClient;
        this.amount = packageClient.getPurchasePrice();
        this.description = packageClient.getDescription();

    }

}
