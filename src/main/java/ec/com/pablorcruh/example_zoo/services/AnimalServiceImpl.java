package ec.com.pablorcruh.example_zoo.services;

import ec.com.pablorcruh.example_zoo.model.Animal;
import ec.com.pablorcruh.example_zoo.repository.AnimalRepository;
import ec.com.pablorcruh.example_zoo.request.AnimalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService{

    private AnimalRepository repository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long createAnimal(AnimalRequest animalRequest) {
        Animal animal = new Animal();
        animal.setCategory(animalRequest.getCategory());
        animal.setName(animalRequest.getName());
        animal = repository.save(animal);
        return animal.getId();
    }

    @Override
    public List<Animal> getAllAnimals() {
        return repository.findAll();
    }
}
