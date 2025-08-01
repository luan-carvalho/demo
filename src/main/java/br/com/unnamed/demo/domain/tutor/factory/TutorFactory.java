package br.com.unnamed.demo.domain.tutor.factory;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Address;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.PersonInfo;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Phone;

public class TutorFactory {

    public static Tutor create(String name, Address address, String phone, LocalDate birthDate) {

        PersonInfo tutorInfo = new PersonInfo(name, new Phone(phone), address, birthDate);
        Tutor tutor = new Tutor(null, tutorInfo, Collections.emptyList(), Status.ACTIVE);
        return tutor;

    }

    public static Tutor createWithPets(String name, Address address, String phone, LocalDate birthDate, List<Pet> pets) {

        PersonInfo tutorInfo = new PersonInfo(name, new Phone(phone), address, birthDate);
        Tutor tutor = new Tutor(null, tutorInfo, pets, Status.ACTIVE);
        return tutor;

    }
    
}
