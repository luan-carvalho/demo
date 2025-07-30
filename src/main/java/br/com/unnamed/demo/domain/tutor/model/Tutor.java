package br.com.unnamed.demo.domain.tutor.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.unnamed.demo.domain.shared.model.Address;
import br.com.unnamed.demo.domain.shared.model.Email;
import br.com.unnamed.demo.domain.shared.model.PersonInfo;
import br.com.unnamed.demo.domain.shared.model.Phone;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonInfo info;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tutor_id")
    private List<Pet> pets;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Tutor() {

        this.pets = new ArrayList<>();
        this.status = Status.ACTIVE;

    }

    public Tutor(Long id, PersonInfo info, List<Pet> pets, Status status) {
        this.id = id;
        this.info = info;
        this.pets = pets;
        this.status = status;
    }

    public String getName() {

        return info.getName();

    }

    public Address getAddress() {

        return info.getAddress();

    }

    public Phone getPhone() {

        return info.getPhone();

    }

    public Email getEmail() {

        return info.getEmail();

    }

    public LocalDate getBirthDate() {

        return info.getBirthDate();

    }

    public long getAge() {

        return info.getAge();

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

    public List<Pet> getInactivePets() {

        return pets.stream().filter(Predicate.not(Pet::isActive)).toList();

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

    public void updateTutorInfo(Phone phone, Email email, String name, Address address, LocalDate birthDate) {

        this.info = new PersonInfo(name, phone, email, address, birthDate);

    }

    public void updatePetInfo(Pet pet) {

        getOwnedPet(pet.getId()).updateInfo(
                pet.getName(),
                pet.getSize(),
                pet.getSpecie(),
                pet.getBreed(),
                pet.getCoatColor(),
                pet.getBirthDate());

    }

    public Long getId() {
        return id;
    }

    public PersonInfo getInfo() {
        return info;
    }

    public Status getStatus() {
        return status;
    }

}
