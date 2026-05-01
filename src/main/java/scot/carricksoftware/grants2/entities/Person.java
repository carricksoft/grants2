/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    public static final int FIELD_SIZE = 128;

    @Id
    @GeneratedValue(generator="UUID")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length=36, columnDefinition="varchar(36)", updatable=false, nullable=false)
    private UUID id;

    @Version
    private Integer version;

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

    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime createdDate;

    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime updatedDate;
}
