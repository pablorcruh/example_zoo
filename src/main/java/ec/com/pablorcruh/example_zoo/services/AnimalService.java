package ec.com.pablorcruh.example_zoo.services;

import ec.com.pablorcruh.example_zoo.request.AnimalRequest;

public interface AnimalService {
    Long createAnimal(AnimalRequest animalRequest);
}
