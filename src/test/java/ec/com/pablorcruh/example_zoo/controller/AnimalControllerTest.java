package ec.com.pablorcruh.example_zoo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.pablorcruh.example_zoo.exceptions.AnimalNotFoundException;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AnimalController.class)
@ActiveProfiles("test")
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

    @Test
    public void getAnimalWithIdOneShouldReturnAnAnimal() throws Exception {
        when(animalService.getAnimalById(1L)).thenReturn(createAnimal(1L, "alex", "mammal"));
        this.mockMvc.perform(get("/api/animals/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("alex")))
                .andExpect(jsonPath("$.category", is("mammal")));
    }

    @Test
    public void getAnimalWithUnknownIdShouldReturn404() throws Exception {
        when(animalService.getAnimalById(42L)).thenThrow(new AnimalNotFoundException("Animal not Found"));
        this.mockMvc.perform(get("/api/animals/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAnimalWithKnownIdShouldReturnUpdatedAnimal() throws Exception {
        AnimalRequest animalRequest = new AnimalRequest();
        animalRequest.setName("alex the lion");
        animalRequest.setCategory("mammal");

        when(animalService.updateAnimal(eq(1L), animalRequestArgumentCaptor.capture()))
                .thenReturn(createAnimal(1L, "alex the lion", "mammal"));

        this.mockMvc
                .perform(put("/api/animals/1")
                    .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(animalRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("alex the lion")))
                .andExpect(jsonPath("$.category", is("mammal")));

        assertThat(animalRequestArgumentCaptor.getValue().getName(), is("alex the lion"));
        assertThat(animalRequestArgumentCaptor.getValue().getCategory(), is("mammal"));

    }

    @Test
    public void updateAnimalWithUnknownIdShouldReturn404() throws Exception {
        AnimalRequest animalRequest = new AnimalRequest();
        animalRequest.setCategory("mammal");
        animalRequest.setName("alex the lion");
         when(animalService.updateAnimal(eq(42L), animalRequestArgumentCaptor.capture()))
                 .thenThrow(new AnimalNotFoundException("Animal not Found"));
         this.mockMvc.perform(put("/api/animals/42")
                    .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(animalRequest)))
                 .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAnimalWithIdOne() throws Exception {
        when(animalService.deleteById(1L)).thenReturn(1L);
        this.mockMvc.perform(delete("/api/animals/1"))
                .andExpect(status().is2xxSuccessful());
    }

    private Animal createAnimal(Long id, String name, String category){
        Animal animal = new Animal();
        animal.setId(id);
        animal.setName(name);
        animal.setCategory(category);
        return animal;
    }

}
