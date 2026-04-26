/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Data
public class Person {
    private UUID id;
    private Integer version;
    private String firstName;
    private String secondName;
    private String yearOfBirth;
    private String yearOfDeath;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
