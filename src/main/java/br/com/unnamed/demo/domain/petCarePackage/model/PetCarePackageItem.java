package br.com.unnamed.demo.domain.petCarePackage.model;

import br.com.unnamed.demo.domain.petCare.model.PetCare;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class PetCarePackageItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_care_id")
    private PetCare petCare;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_care_package_id")
    private PetCarePackage petCarePackage;

    public PetCarePackageItem(PetCare petCare, Integer quantity, PetCarePackage petCarePackage) {
        this.petCare = petCare;
        this.quantity = quantity;
        this.petCarePackage = petCarePackage;
    }

}
