package br.com.unnamed.demo.domain.petCarePackage.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.unnamed.demo.domain.petCarePackage.enums.PetCarePackageClientStatus;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class PetCarePackageClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate purchaseDate;
    private LocalDate expiringDate;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "pet_care_package_id")
    private PetCarePackage petCarePackage;
    private BigDecimal purchasePrice;
    private String description;

    private PetCarePackageClientStatus status;

    public PetCarePackageClient(LocalDate purchaseDate, Tutor tutor, PetCarePackage petCarePackage) {

        this.purchaseDate = purchaseDate;
        this.purchasePrice = petCarePackage.getPrice();
        this.description = petCarePackage.getDescription();
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
