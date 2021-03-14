package ec.com.pablorcruh.example_zoo.request;

import javax.validation.constraints.NotEmpty;

public class AnimalRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String category;
}
