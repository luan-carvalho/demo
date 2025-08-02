package br.com.unnamed.demo.domain.tutor.mapper;

import br.com.unnamed.demo.domain.tutor.dtos.PersonInfoDto;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.PersonInfo;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Phone;

public class PersonInfoMapper {

    public static PersonInfo toEntity(PersonInfoDto info) {

        return new PersonInfo(
                info.name(),
                new Phone(info.phone()));

    }

    public static PersonInfoDto toDto(PersonInfo info) {

        return new PersonInfoDto(
                info.getName(),
                info.getPhone().getValue());

    }
    

}
