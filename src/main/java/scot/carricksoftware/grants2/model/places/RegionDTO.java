/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.model.places;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.entities.places.Place;
import scot.carricksoftware.grants2.model.BaseEntityDTO;

import java.util.Set;


@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO extends BaseEntityDTO {

    @NotBlank
    @NotNull
    @JsonProperty("name")
    private String name;


    private Country country;


    private Set<Place> places;

}
