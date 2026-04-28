/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("recordedYearOfBirth")
    private String recordedYearOfBirth;


    @JsonProperty("certifiedYearOfBirth")
    private String certifiedYearOfBirth;


    @JsonProperty("certifiedYearOfDeath")
    private String certifiedYearOfDeath;


    @JsonProperty("createdDate")
    private LocalDateTime createdDate;


    @JsonProperty("updatedDate")
    private LocalDateTime updatedDate;
}
