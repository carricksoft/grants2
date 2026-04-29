/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.entities.Person;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.mappers.PersonMapper;
import scot.carricksoftware.grants2.model.PersonDTO;
import scot.carricksoftware.grants2.repositories.PersonRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class PersonControllerUpdateIT {
    @Autowired
    PersonController personController;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonMapper personMapper;


    @Test
    @Transactional
    @Rollback
    void updateExistingPersonTest() {
        Person person = personRepository.findAll().getFirst();
        PersonDTO personDTO = personMapper.personToPersonDto(person);
        personDTO.setId(null);
        personDTO.setVersion(null);
        final String firstName = "Martha";
        personDTO.setFirstName(firstName);

        @SuppressWarnings("rawtypes") ResponseEntity responseEntity = personController.upDateById(person.getId(), personDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Person updatedPerson = personRepository.findAll().getFirst();
        assertThat(updatedPerson.getFirstName()).isEqualTo(firstName);
    }

    @Test
    void updateExistingPersonNotFoundTest() {
        assertThrows(NotFoundException.class, () -> personController.upDateById(UUID.randomUUID(), PersonDTO.builder().build()));
    }



}