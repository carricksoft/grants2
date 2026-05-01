/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO extends BaseEntityDTO{

    @NotBlank
    @NotNull
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank
    @NotNull
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("recordedYearOfBirth")
    private String recordedYearOfBirth;

    @JsonProperty("certifiedYearOfBirth")
    private String certifiedYearOfBirth;

    @JsonProperty("certifiedYearOfDeath")
    private String certifiedYearOfDeath;

}
