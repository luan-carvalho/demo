package br.com.unnamed.demo.domain.tutor.factory;

import java.util.Collections;
import java.util.List;

import br.com.unnamed.demo.domain.tutor.model.Pet;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.enums.Status;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.PersonInfo;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Phone;

public class TutorFactory {

    public static Tutor create(String name,  String phone) {

        PersonInfo tutorInfo = new PersonInfo(name, new Phone(phone));
        Tutor tutor = new Tutor(null, tutorInfo, Collections.emptyList(), Status.ACTIVE);
        return tutor;

    }

    public static Tutor createWithPets(String name, String phone,  List<Pet> pets) {

        PersonInfo tutorInfo = new PersonInfo(name, new Phone(phone));
        Tutor tutor = new Tutor(null, tutorInfo, pets, Status.ACTIVE);
        return tutor;

    }
    
}
