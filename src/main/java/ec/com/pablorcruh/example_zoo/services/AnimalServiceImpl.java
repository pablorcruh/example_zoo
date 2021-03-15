package ec.com.pablorcruh.example_zoo.services;

import ec.com.pablorcruh.example_zoo.exceptions.AnimalNotFoundException;
import ec.com.pablorcruh.example_zoo.model.Animal;
import ec.com.pablorcruh.example_zoo.repository.AnimalRepository;
import ec.com.pablorcruh.example_zoo.request.AnimalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Animal getAnimalById(Long id) {
        Optional<Animal> animalFromDatabase = repository.findById(id);
        if(animalFromDatabase.isEmpty()){
            throw new AnimalNotFoundException("Animal not Found");
        }
        return animalFromDatabase.get();
    }

    @Override
    @Transactional
    public Animal updateAnimal(Long id, AnimalRequest animalRequest) {
        Optional<Animal> animalFromDatabase = repository.findById(id);
        if(animalFromDatabase.isEmpty()){
            throw new AnimalNotFoundException("Animal not Found");
        }
        Animal animalToUpdate = animalFromDatabase.get();
        animalToUpdate.setName(animalRequest.getName());
        animalToUpdate.setCategory(animalRequest.getCategory());
        return animalToUpdate;
    }
}
