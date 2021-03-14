package ec.com.pablorcruh.example_zoo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.pablorcruh.example_zoo.model.Animal;
import ec.com.pablorcruh.example_zoo.request.AnimalRequest;
import ec.com.pablorcruh.example_zoo.services.AnimalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnimalService animalService;

    @Captor
    private ArgumentCaptor<AnimalRequest> animalRequestArgumentCaptor;

    @Test
    public void postingNewAnimalShouldCreateNewAnimal() throws Exception {
        AnimalRequest animalRequest = new AnimalRequest();
        animalRequest.setCategory("mammal");
        animalRequest.setName("alex");
        when(animalService.createAnimal(animalRequestArgumentCaptor.capture())).thenReturn(1L);

        this.mockMvc
                .perform(post("/api/animals")
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(animalRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/animals/1"));
        assertThat(animalRequestArgumentCaptor.getValue().getCategory(), is("mammal"));
        assertThat(animalRequestArgumentCaptor.getValue().getName(), is("alex"));

    }

    @Test
    public void allAnimalsEndpointShouldReturnTwoAnimals() throws Exception {
        when(animalService.getAllAnimals()).thenReturn(List.of(
                createAnimal(1L, "alex", "mammal"),
                createAnimal(2L, "snake",  "reptile")
        ));

        this.mockMvc
                .perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("alex")))
                .andExpect(jsonPath("$[0].category", is("mammal")));
    }

    private Animal createAnimal(Long id, String name, String category){
        Animal animal = new Animal();
        animal.setId(id);
        animal.setName(name);
        animal.setCategory(category);
        return animal;
    }

}
