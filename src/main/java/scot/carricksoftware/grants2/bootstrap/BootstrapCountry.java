/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapCountry implements CommandLineRunner {

    private final CountryRepository countryRepository;

    @Override
    public void run(@SuppressWarnings("NullableProblems") String... args) {
        loadCountryData();
    }

    private void loadCountryData() {
        if (0 == countryRepository.count()) {
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

}
