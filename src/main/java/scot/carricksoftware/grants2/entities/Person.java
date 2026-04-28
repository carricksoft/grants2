/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    private UUID id;
    @Version
    private Integer version;

    private String firstName;
    private String lastName;
    private String recordedYearOfBirth;
    private String certifiedYearOfBirth;
    private String certifiedYearOfDeath;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
