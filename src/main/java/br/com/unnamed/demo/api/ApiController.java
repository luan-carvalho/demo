package br.com.unnamed.demo.api;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.unnamed.demo.domain.tutor.model.valueObjects.Breed;
import br.com.unnamed.demo.domain.tutor.service.PetInfoService;

@Controller
@RequestMapping("api")
public class ApiController {

    private final PetInfoService petInfoService;

    public ApiController(PetInfoService petInfoService) {
        this.petInfoService = petInfoService;
    }

    @GetMapping("/specie/{specieId}/breeds")
    @ResponseBody
    public List<Breed> getBreedsBasedOnSpecies(@PathVariable Long specieId) {
        return petInfoService.findAllBreedsFromSpecieId(specieId);
    }

}
