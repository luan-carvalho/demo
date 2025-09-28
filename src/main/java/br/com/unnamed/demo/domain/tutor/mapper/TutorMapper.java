package br.com.unnamed.demo.domain.tutor.mapper;

import java.util.List;

import br.com.unnamed.demo.domain.tutor.dtos.PersonInfoDto;
import br.com.unnamed.demo.domain.tutor.dtos.TutorFormDto;
import br.com.unnamed.demo.domain.tutor.dtos.TutorGridDto;
import br.com.unnamed.demo.domain.tutor.model.Tutor;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.PersonInfo;

public class TutorMapper {

    public static Tutor toEntity(TutorFormDto dto) {

        return new Tutor(null, new PersonInfo(dto.info().name(), dto.info().phone()), dto.status(), dto.group());

    }

    public static TutorFormDto toForm(Tutor tutor) {

        return new TutorFormDto(
                tutor.getId(),
                new PersonInfoDto(tutor.getName(),
                        tutor.getPhone().getValue()),
                tutor.getGroup(),
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
