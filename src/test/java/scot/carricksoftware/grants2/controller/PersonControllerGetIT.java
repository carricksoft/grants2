/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scot.carricksoftware.grants2.entities.Person;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.model.PersonDTO;
import scot.carricksoftware.grants2.repositories.PersonRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class PersonControllerGetIT {
    @Autowired
    PersonController personController;

    @Autowired
    PersonRepository personRepository;


    @Test
    void getPersonByIdTest() {
        Person person = personRepository.findAll().getFirst();
        PersonDTO dto = personController.getPersonById(person.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void personIsNotFoundTest(){
        assertThrows(NotFoundException.class, () -> personController.getPersonById(UUID.randomUUID()));
    }


}