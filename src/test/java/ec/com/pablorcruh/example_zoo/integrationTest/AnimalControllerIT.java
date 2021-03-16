package ec.com.pablorcruh.example_zoo.integrationTest;

import com.fasterxml.jackson.databind.JsonNode;
import ec.com.pablorcruh.example_zoo.model.Animal;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AnimalControllerIT {
    @LocalServerPort
    int randomServerPort;

    private TestRestTemplate testRestTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void setup(){
        this.testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void deleteKnownEntityShouldReturn404AfterDeletion(){
        long animalId = 1;
        String baseUrl = "http://localhost:" + randomServerPort;

        ResponseEntity<JsonNode> firstResult = this.testRestTemplate
                .getForEntity(baseUrl + "/api/animals/" + animalId, JsonNode.class);
        assertThat(firstResult.getStatusCode(), is(HttpStatus.OK));

        this.testRestTemplate.delete(baseUrl + "/api/animals/" + animalId);

        ResponseEntity<JsonNode> secondResult = this.testRestTemplate
                .getForEntity(baseUrl + "/api/animals/" + animalId, JsonNode.class);
        assertThat(secondResult.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void addNewAnimalToDatabase(){
        Animal animal = new Animal();
        animal.setName("animal test");
        animal.setCategory("category test");
        HttpEntity<Animal> entity = new HttpEntity<Animal>(animal, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/api/animals/"),
                HttpMethod.POST, entity, String.class);
        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
        assertTrue(actual.contains("/api/animals"));
    }


    @Test
    public void retrieveKnownIdAnimalsFromDatabase() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/api/animals/3"),
                HttpMethod.GET, entity, String.class);
        String expected = "{\"id\":3,\"name\":\"animal test\",\"category\":\"category test\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri){
        return "http://localhost:" + randomServerPort + uri;
    }
}
