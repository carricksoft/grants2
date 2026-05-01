/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.PersonController;
import scot.carricksoftware.grants2.entities.Person;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.model.PersonDTO;
import scot.carricksoftware.grants2.repositories.PersonRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class PersonControllerDeleteIT {
    @Autowired
    PersonController personController;

    @Autowired
    PersonRepository personRepository;


    @Test
    @Transactional
    @Rollback
    void deletePersonByIDTest() {
        Person person = personRepository.findAll().getFirst();

        @SuppressWarnings("rawtypes") ResponseEntity responseEntity = personController.deleteById(person.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(personRepository.findById(person.getId())).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void deletePersonNotFoundTest() {
        assertThrows(NotFoundException.class, () -> personController.upDateById(UUID.randomUUID(), PersonDTO.builder().build()));
    }




}