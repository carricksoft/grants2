/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class CountryRegionRepositoryTest {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RegionRepository regionRepository;

    Country testCountry;
    Region testRegion;
    Region testRegion2;

    @BeforeEach
    void setUp() {
        testCountry = countryRepository.findAll().getFirst();
        testRegion = regionRepository.findAll().getFirst();
        testRegion2 = regionRepository.findAll().getLast();
    }

    @Test
    @Transactional
    @Rollback
    void CountryRegionTest() {
       testRegion.setCountry(testCountry);
       testRegion2.setCountry(testCountry);

       countryRepository.flush();
       regionRepository.flush();
        assertThat(testRegion.getCountry().getName()).isEqualTo(testCountry.getName());
        assertThat(testRegion2.getCountry().getName()).isEqualTo(testCountry.getName());
        assertThat(testCountry.getRegions().contains(testRegion)).isTrue();
        assertThat(testCountry.getRegions().contains(testRegion2)).isTrue();
    }




}

