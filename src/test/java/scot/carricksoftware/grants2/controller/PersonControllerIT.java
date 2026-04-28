/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.entities.Person;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.model.PersonDTO;
import scot.carricksoftware.grants2.repositories.PersonRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class PersonControllerIT {
    @Autowired
    PersonController personController;

    @Autowired
    PersonRepository personRepository;

    @Test
    void listPeopleTest() {
        List<PersonDTO> dtos = personController.listPeople();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void emptyListTest() {
        personRepository.deleteAll();
        List<PersonDTO> dtos = personController.listPeople();
        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void getByIdTest() {
        Person person = personRepository.findAll().getFirst();
        PersonDTO dto = personController.getPersonById(person.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void personIsNotFoundTest(){
        assertThrows(NotFoundException.class, () -> personController.getPersonById(UUID.randomUUID()));
    }
}