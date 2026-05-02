/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.country;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.places.CountryController;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class CountryControllerListIT {
    @Autowired
    CountryController countryController;

    @Autowired
    CountryRepository countryRepository;


    @Test
    void listPeopleTest() {
        List<CountryDTO> dtoList = countryController.listCountries(null);
        assertThat(dtoList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void emptyListTest() {
        countryRepository.deleteAll();
        List<CountryDTO> dtoList = countryController.listCountries(null);
        assertThat(dtoList.size()).isEqualTo(0);
    }


}