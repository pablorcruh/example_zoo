package ec.com.pablorcruh.example_zoo.services;

import ec.com.pablorcruh.example_zoo.model.Animal;
import ec.com.pablorcruh.example_zoo.request.AnimalRequest;

import java.util.List;

public interface AnimalService {
    Long createAnimal(AnimalRequest animalRequest);

    List<Animal> getAllAnimals();

    Animal getAnimalById(Long id);

    Animal updateAnimal(Long id, AnimalRequest animalRequest);

    Long deleteById(Long id);
}
