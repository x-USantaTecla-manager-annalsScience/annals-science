package es.upm.annalsscience.infrastructure.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatePersonDTO {
    private String name;
    private String surname;
    private String birthDate;
    private String deathDate;
    private String description;
    private String imageUrl;
    private String wikiUrl;
    private List<Long> categoriesId;
}
