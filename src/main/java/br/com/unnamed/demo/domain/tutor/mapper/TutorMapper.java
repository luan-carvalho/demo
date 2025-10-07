package br.com.unnamed.demo.domain.tutor.mapper;

import java.util.List;

import br.com.unnamed.demo.domain.tutor.dtos.TutorFormDto;
import br.com.unnamed.demo.domain.tutor.dtos.TutorGridDto;
import br.com.unnamed.demo.domain.tutor.model.Tutor;

public class TutorMapper {

    public static Tutor toEntity(TutorFormDto dto) {

        return new Tutor(dto.name(), dto.phone());

    }

    public static TutorFormDto toForm(Tutor tutor) {

        return new TutorFormDto(
                tutor.getId(),
                tutor.getName(),
                tutor.getPhone(),
                tutor.getGroup(),
                tutor.getAllPets(),
                tutor.getStatus());

    }

    public static TutorGridDto toGrid(Tutor tutor) {

        return new TutorGridDto(
                tutor.getId(),
                tutor.getName(),
                tutor.getActivePets());

    }

    public static List<TutorGridDto> toGridList(List<Tutor> tutors) {

        return tutors.stream().map(TutorMapper::toGrid).toList();

    }

}
