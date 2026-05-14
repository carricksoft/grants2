/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.country;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.rest.places.country.CountryController;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SuppressWarnings("ClassHasNoToStringMethod")
@SpringBootTest
class CountryControllerGetIT {
    @Autowired
    CountryController countryController;

    @Autowired
    CountryRepository countryRepository;


    @Test
    @Transactional
    void getCountryByIdTest() {
        Country country = countryRepository.findAll().get(0);
        CountryDTO dto = countryController.getCountryById(country.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void countryIsNotFoundTest(){
        assertThrows(NotFoundException.class, () -> countryController.getCountryById(UUID.randomUUID()));
    }


}