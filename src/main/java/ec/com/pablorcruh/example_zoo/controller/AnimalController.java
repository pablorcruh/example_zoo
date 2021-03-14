package ec.com.pablorcruh.example_zoo.controller;

import ec.com.pablorcruh.example_zoo.model.Animal;
import ec.com.pablorcruh.example_zoo.request.AnimalRequest;
import ec.com.pablorcruh.example_zoo.services.AnimalService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping
    public ResponseEntity<Void> createAnimal(@Valid @RequestBody AnimalRequest animalRequest, UriComponentsBuilder uriComponentsBuilder){
        Long primaryKey = animalService.createAnimal(animalRequest);

        UriComponents uriComponents = uriComponentsBuilder.path("/api/animals/{id}").buildAndExpand(primaryKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Animal>> getAllAnimals(){
        return ResponseEntity.ok(animalService.getAllAnimals());
    }
}
