/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person extends BaseEntity{

    public static final int FIELD_SIZE = 128;


    @NotNull
    @NotBlank
    @Size(max=FIELD_SIZE)
    @Column(length=128)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max=FIELD_SIZE)
    @Column(length=128)
    private String lastName;

    @Column(length=4, columnDefinition="varchar(4)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String recordedYearOfBirth;

    @Column(length=4, columnDefinition="varchar(4)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String certifiedYearOfBirth;

    @Column(length=4, columnDefinition="varchar(4)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String certifiedYearOfDeath;


}
