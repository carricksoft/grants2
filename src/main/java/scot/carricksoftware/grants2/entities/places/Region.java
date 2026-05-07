/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.entities.places;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import scot.carricksoftware.grants2.entities.BaseEntity;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Region extends BaseEntity {

    @NotNull
    @NotBlank
    @Size(max=FIELD_SIZE)
    @Column(length=128)
    private String name;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy="region")
    private Set<Place> places;
}
