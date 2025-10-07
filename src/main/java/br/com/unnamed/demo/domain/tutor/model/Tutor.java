package br.com.unnamed.demo.domain.tutor.model;

import java.util.ArrayList;
import java.util.List;

import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "tutor_id")
    private List<Pet> pets;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private TutorGroup group;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Tutor() {

        this.pets = new ArrayList<>();
        this.status = Status.ACTIVE;

    }

    public Tutor(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.pets = new ArrayList<>();
        this.status = Status.INACTIVE;
    }

    public Pet getOwnedPet(Long petId) {

        return pets.stream().filter(p -> p.getId().equals(petId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

    }

    public List<Pet> getAllPets() {

        return List.copyOf(pets);

    }

    public List<Pet> getActivePets() {

        return pets.stream().filter(Pet::isActive).toList();

    }

    public void addPet(Pet pet) {

        pets.add(pet);

    }

    public void removePet(Pet pet) {

        pets.remove(pet);

    }

    public void deactivate() {

        this.status = Status.INACTIVE;
        this.pets.forEach(Pet::deactivate);

    }

    public void activate() {

        this.status = Status.ACTIVE;
        this.pets.forEach(Pet::activate);

    }

    public void deactivatePet(Long petId) {

        this.pets.stream().filter(p -> p.getId().equals(petId)).findFirst().ifPresent(Pet::deactivate);

    }

    public void activatePet(Long petId) {

        this.pets.stream().filter(p -> p.getId().equals(petId)).findFirst().ifPresent(Pet::activate);

    }

    public boolean isActive() {

        return status == Status.ACTIVE;

    }

    public boolean isInactive() {

        return status == Status.INACTIVE;

    }

    public void updateTutorInfo(String phone, String name) {

        this.name = name;
        this.phone = phone;

    }

    public void updateTutorGroup(TutorGroup group) {

        this.group = group;

    }

    public void updatePetName(Long petId, String name) {

        getOwnedPet(petId).updateName(name);

    }

}
