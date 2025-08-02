package br.com.unnamed.demo.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.unnamed.demo.domain.tutor.model.valueObjects.Breed;
import br.com.unnamed.demo.domain.tutor.service.PetInfoService;
import br.com.unnamed.demo.domain.tutor.service.TutorService;

@Controller
@RequestMapping("api")
public class ApiController {

    private final PetInfoService petInfoService;
    private final TutorService tutorService;

    public ApiController(PetInfoService petInfoService, TutorService tutorService) {
        this.petInfoService = petInfoService;
        this.tutorService = tutorService;
    }

    @GetMapping("/breed/fetch")
    @ResponseBody
    public List<Breed> getBreedsBasedOnSpecies(@RequestParam Long specieId) {
        return petInfoService.findAllBreedsFromSpecieId(specieId);
    }

}
