package ec.com.pablorcruh.example_zoo.repository;

import ec.com.pablorcruh.example_zoo.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal,  Long> {
}
