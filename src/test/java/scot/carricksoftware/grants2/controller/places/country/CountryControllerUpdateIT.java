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
import scot.carricksoftware.grants2.controller.places.CountryController;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.mappers.places.CountryMapper;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CountryControllerUpdateIT {
    @Autowired
    CountryController countryController;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CountryMapper countryMapper;


    @Test
    @Transactional
    @Rollback
    void updateExistingCountryTest() {
        Country country = countryRepository.findAll().get(0);
        CountryDTO countryDTO = countryMapper.countryToCountryDto(country);
        countryDTO.setId(null);
        countryDTO.setVersion(null);
        final String name = "Martha";
        countryDTO.setName(name);

        @SuppressWarnings("rawtypes") ResponseEntity responseEntity = countryController.upDateById(country.getId(), countryDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Country updatedCountry = countryRepository.findAll().get(0);
        assertThat(updatedCountry.getName()).isEqualTo(name);
    }

    @Test
    @Transactional
    @Rollback
    void updateExistingCountryNotFoundTest() {
        assertThrows(NotFoundException.class, () -> countryController.upDateById(UUID.randomUUID(), CountryDTO.builder().build()));
    }



}