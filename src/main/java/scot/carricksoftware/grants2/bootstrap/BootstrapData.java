/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.bootstrap;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import scot.carricksoftware.grants2.entities.Person;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.repositories.PersonRepository;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;

    @Override
    public void run(@SuppressWarnings("NullableProblems") String... args) {
        loadPersonData();
        loadCountryData();
        loadRegionData();
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

    private void loadCountryData() {
        if (countryRepository.count() == 0) {
            Country country1 = Country.builder()
                    .name("Country 1")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Country country2 = Country.builder()
                    .name("Country 2")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Country country3 = Country.builder()
                    .name("Country 3")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();



            countryRepository.saveAll(Arrays.asList(country1, country2, country3));
        }

    }

    private void loadRegionData() {
        if (regionRepository.count() == 0) {
            Region region1 = Region.builder()
                    .name("Region 1")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Region region2 = Region.builder()
                    .name("Region 2")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Region region3 = Region.builder()
                    .name("Region 3")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();


            regionRepository.saveAll(Arrays.asList(region1, region2, region3));
        }
    }


    }
