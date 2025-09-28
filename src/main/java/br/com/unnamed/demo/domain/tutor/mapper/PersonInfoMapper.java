package br.com.unnamed.demo.domain.tutor.mapper;

import br.com.unnamed.demo.domain.tutor.dtos.PersonInfoDto;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.PersonInfo;

public class PersonInfoMapper {

    public static PersonInfoDto toDto(PersonInfo info) {

        return new PersonInfoDto(
                info.getName(),
                info.getPhone().getValue());

    }
    

}
