package br.com.unnamed.demo.domain.tutor.mapper;

import br.com.unnamed.demo.domain.shared.model.Email;
import br.com.unnamed.demo.domain.shared.model.PersonInfo;
import br.com.unnamed.demo.domain.shared.model.Phone;
import br.com.unnamed.demo.domain.tutor.dtos.PersonInfoDto;

public class PersonInfoMapper {

    public static PersonInfo toEntity(PersonInfoDto info) {

        return new PersonInfo(
                info.name(),
                new Phone(info.phone()),
                new Email(info.email()),
                AddressMapper.toEntity(info.address()),
                info.birthDate());

    }

    public static PersonInfoDto toDto(PersonInfo info) {

        return new PersonInfoDto(
                info.getName(),
                AddressMapper.toDto(info.getAddress()),
                info.getPhone().getValue(),
                info.getEmail().getValue(),
                info.getBirthDate());

    }
    

}
