/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.country;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.rest.places.country.CountryController;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SuppressWarnings("ClassHasNoToStringMethod")
@SpringBootTest
class CountryControllerListIT {
    @Autowired
    CountryController countryController;

    @Autowired
    CountryRepository countryRepository;

    @Disabled
    @Test
    void listCountryTest() {
        Page<CountryDTO> dtoList = countryController.listCountries(null, 1, 25);
        assertThat(dtoList.getContent().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void emptyListTest() {
        countryRepository.deleteAll();
        Page<CountryDTO> dtoList = countryController.listCountries(null, 1, 25);
        assertThat(dtoList.getContent().size()).isEqualTo(0);
    }


}