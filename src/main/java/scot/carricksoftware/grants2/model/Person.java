/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.model;

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
public class Person {
    private UUID id;
    private Integer version;
    private String firstName;
    private String lastName;
    private String recordedYearOfBirth;
    private String certifiedYearOfBirth;
    private String certifiedYearOfDeath;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
