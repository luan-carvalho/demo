package br.com.unnamed.demo.domain.petCarePackage.model;

import java.time.LocalDate;
import java.util.List;

import br.com.unnamed.demo.domain.payment.model.Payment;
import br.com.unnamed.demo.domain.petCarePackage.enums.PetCarePackageClientStatus;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class PetCarePackagePurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate purchaseDate;
    private LocalDate expiringDate;

    private Tutor tutor;

    private PetCarePackage petCarePackage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;

    private PetCarePackageClientStatus status;

    public PetCarePackagePurchase(LocalDate purchaseDate, Tutor tutor, PetCarePackage petCarePackage) {

        this.purchaseDate = purchaseDate;
        this.tutor = tutor;
        this.petCarePackage = petCarePackage;
        this.expiringDate.plusDays(petCarePackage.getValidityPeriod());
        this.status = PetCarePackageClientStatus.ACTIVE;

    }

    public boolean isActive() {

        return this.status == PetCarePackageClientStatus.ACTIVE;

    }

    public boolean hasExpired() {

        return this.status == PetCarePackageClientStatus.EXPIRED;

    }

}
