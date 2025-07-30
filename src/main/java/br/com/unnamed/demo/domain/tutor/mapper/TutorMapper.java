package br.com.unnamed.demo.domain.tutor.mapper;

import java.util.List;

import br.com.unnamed.demo.domain.tutor.dtos.PersonInfoDto;
import br.com.unnamed.demo.domain.tutor.dtos.TutorFormDto;
import br.com.unnamed.demo.domain.tutor.dtos.TutorGridDto;
import br.com.unnamed.demo.domain.tutor.factory.TutorFactory;
import br.com.unnamed.demo.domain.tutor.model.Tutor;

public class TutorMapper {

    public static Tutor toEntity(TutorFormDto dto) {

        return TutorFactory.create(
                dto.info().name(),
                AddressMapper.toEntity(dto.info().address()),
                dto.info().phone(),
                dto.info().email(),
                dto.info().birthDate());

    }

    public static TutorFormDto toForm(Tutor tutor) {

        return new TutorFormDto(
                tutor.getId(),
                new PersonInfoDto(tutor.getName(),
                        AddressMapper.toDto(tutor.getAddress()),
                        tutor.getPhone().getValue(),
                        tutor.getEmail().getValue(),
                        tutor.getBirthDate()),
                tutor.getAllPets(),
                tutor.getStatus());

    }

    public static TutorGridDto toGrid(Tutor tutor) {

        return new TutorGridDto(
                tutor.getId(),
                tutor.getName(),
                tutor.getAllPets());

    }

    public static List<TutorGridDto> toGridList(List<Tutor> tutors) {

        return tutors.stream().map(TutorMapper::toGrid).toList();

    }

}
