package br.com.unnamed.demo.domain.tutor.factory;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import br.com.unnamed.demo.domain.shared.model.Address;
import br.com.unnamed.demo.domain.shared.model.Email;
import br.com.unnamed.demo.domain.shared.model.PersonInfo;
import br.com.unnamed.demo.domain.shared.model.Phone;
import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;

public class TutorFactory {

    public static Tutor create(String name, Address address, String phone, String email, LocalDate birthDate) {

        PersonInfo tutorInfo = new PersonInfo(name, new Phone(phone), new Email(email), address, birthDate);
        Tutor tutor = new Tutor(null, tutorInfo, Collections.emptyList(), Status.ACTIVE);
        return tutor;

    }

    public static Tutor createWithPets(String name, Address address, String phone, String email, LocalDate birthDate, List<Pet> pets) {

        PersonInfo tutorInfo = new PersonInfo(name, new Phone(phone), new Email(email), address, birthDate);
        Tutor tutor = new Tutor(null, tutorInfo, pets, Status.ACTIVE);
        return tutor;

    }
    
}
