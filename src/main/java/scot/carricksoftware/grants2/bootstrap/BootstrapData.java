/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.bootstrap;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import scot.carricksoftware.grants2.entities.Person;
import scot.carricksoftware.grants2.repositories.PersonRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final PersonRepository personRepository;

    @Override
    public void run(@SuppressWarnings("NullableProblems") String... args) {
        loadPersonData();
    }
    
    private void loadPersonData() {
        if (personRepository.count() == 0) {
            Person person1 = Person.builder()
                    .firstName("Person 1F")
                    .lastName("Person 1L")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Person person2 = Person.builder()
                    .firstName("Person 2F")
                    .lastName("Person 2L")
                    .version(1)
                    .certifiedYearOfBirth("1953")
                    .certifiedYearOfDeath("2050")
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Person person3 = Person.builder()
                    .firstName("Person 3F")
                    .lastName("Person 3L")
                    .recordedYearOfBirth("1955")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            personRepository.saveAll(Arrays.asList(person1, person2, person3));
        }

    }


}
