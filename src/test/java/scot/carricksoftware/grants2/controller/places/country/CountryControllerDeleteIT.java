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
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CountryControllerDeleteIT {
    @Autowired
    CountryController countryController;

    @Autowired
    CountryRepository countryRepository;

    @Test
    @Transactional
    @Rollback
    void deleteCountryByIDTest() {
        Country country = countryRepository.findAll().get(0);
        @SuppressWarnings("rawtypes") ResponseEntity responseEntity = countryController.deleteById(country.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(countryRepository.findById(country.getId())).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void deleteCountryNotFoundTest() {
        assertThrows(NotFoundException.class, () -> countryController.upDateById(UUID.randomUUID(), CountryDTO.builder().build()));
    }




}