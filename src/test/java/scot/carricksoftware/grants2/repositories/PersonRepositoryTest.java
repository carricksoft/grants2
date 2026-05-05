/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import scot.carricksoftware.grants2.bootstrap.BootstrapPerson;
import scot.carricksoftware.grants2.entities.Person;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static scot.carricksoftware.grants2.entities.Person.FIELD_SIZE;


@DataJpaTest
@Import(BootstrapPerson.class)
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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
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

    @Test
    void getPeopleByFirstNameTest() {
        List<Person> list = personRepository.findAllByFirstNameIsLikeIgnoreCase("%person 1f%", null);
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void getPeopleByLastNameTest() {
        List<Person> list = personRepository.findAllByLastNameIsLikeIgnoreCase("%person 1L%", null);
        assertThat(list.size()).isEqualTo(1);
    }

}

