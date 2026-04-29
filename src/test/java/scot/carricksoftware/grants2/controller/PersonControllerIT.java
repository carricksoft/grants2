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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
class PersonControllerIT {
    @Autowired
    PersonController personController;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonMapper personMapper;

    @Test
    void listPeopleTest() {
        List<PersonDTO> dtoList = personController.listPeople();
        assertThat(dtoList.size()).isEqualTo(3);
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
    void getPersonByIdTest() {
        Person person = personRepository.findAll().getFirst();
        PersonDTO dto = personController.getPersonById(person.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void personIsNotFoundTest(){
        assertThrows(NotFoundException.class, () -> personController.getPersonById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void saveNewPersonTest() {
        PersonDTO personDTO = PersonDTO.builder()
                .firstName("New First Name")
                .build();

        @SuppressWarnings("rawtypes") ResponseEntity responseEntity = personController.handlePost(personDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        String[] locationUUID = Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[2]);

        Optional<Person> optionalPerson= personRepository.findById(savedUUID);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            assertThat(person).isNotNull();
        } else {
            fail();
        }
    }

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


}