/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.bootstrap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BootstrapCountryTest {

    @Autowired
    CountryRepository countryRepositoryMock;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    BootstrapCountry bootstrapCountry;


    @Test
    @Transactional
    @Rollback
    void runTest(){
        countryRepository.deleteAll();
        assertThat(countryRepository.count()).isEqualTo(0);
        bootstrapCountry.run();
        assertThat(countryRepository.count()).isEqualTo(3);
    }

}