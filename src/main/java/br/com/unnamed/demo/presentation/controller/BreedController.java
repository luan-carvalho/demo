package br.com.unnamed.demo.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.unnamed.demo.domain.shared.service.ReferenceDataService;
import br.com.unnamed.demo.domain.tutor.model.valueObjects.Breed;

@Controller
@RequestMapping("breed")
public class BreedController {

    private final ReferenceDataService dataService;

    public BreedController(ReferenceDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/fetch")
    @ResponseBody
    public List<Breed> getBreedsBasedOnSpecies(@RequestParam Long specieId) {
        return dataService.findAllBreedsFromSpecieId(specieId);
    }

}
