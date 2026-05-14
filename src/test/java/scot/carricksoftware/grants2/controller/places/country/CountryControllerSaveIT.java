/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.country;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.rest.places.country.CountryController;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


@SuppressWarnings("ClassHasNoToStringMethod")
@SpringBootTest
class CountryControllerSaveIT {
    @Autowired
    CountryController countryController;

    @Autowired
    CountryRepository countryRepository;

    @Test
    @Transactional
    @Rollback
    void saveNewCountryTest() {
        CountryDTO countryDTO = CountryDTO.builder()
                .name("New Name")
                .build();

        @SuppressWarnings("rawtypes") ResponseEntity responseEntity = countryController.handlePost(countryDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        String[] locationUUID = Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[2]);

        Optional<Country> optionalCountry= countryRepository.findById(savedUUID);
        if (optionalCountry.isPresent()) {
            Country country = optionalCountry.get();
            assertThat(country).isNotNull();
        } else {
            fail();
        }
    }

}