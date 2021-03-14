package ec.com.pablorcruh.example_zoo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.pablorcruh.example_zoo.request.AnimalRequest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Captor
    private ArgumentCaptor<AnimalRequest> animalRequestArgumentCaptor;

}
