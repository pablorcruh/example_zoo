package ec.com.pablorcruh.example_zoo.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnimalRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String category;
}
