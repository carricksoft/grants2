/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import scot.carricksoftware.grants2.entities.Person;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static scot.carricksoftware.grants2.entities.Person.FIELD_SIZE;


@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    void saveTest() {
        Person savedPerson = personRepository.save(Person.builder()
                .firstName("Andrew")
                .lastName("Grant")
                .build());
        personRepository.flush();

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getFirstName()).isEqualTo("Andrew");
        assertThat(savedPerson.getLastName()).isEqualTo("Grant");
    }

    @Test
    void firstNameTooLongTest() {
        String testString = longString(FIELD_SIZE + 1);
        assertThrows(ConstraintViolationException.class, ()-> {
            Person savedPerson = personRepository.save(Person.builder()
                    .firstName(testString)
                    .lastName("Grant")
                    .build());
            personRepository.flush();
        });
    }

    @Test
    void lastNameTooLongTest() {
        String testString = longString(FIELD_SIZE + 1);
        assertThrows(ConstraintViolationException.class, ()-> {
            Person savedPerson = personRepository.save(Person.builder()
                    .firstName("Andrew")
                    .lastName(testString)
                    .build());
            personRepository.flush();
        });
    }

    private String longString(@SuppressWarnings("SameParameterValue") int length) {
        StringBuilder output = new StringBuilder("1234567890");
        while (output.length() < length) {
            output.append(output);
        }
        return output.substring(0, length);
    }
}

