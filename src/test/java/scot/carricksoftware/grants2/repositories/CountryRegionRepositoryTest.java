/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;


@SpringBootTest
class CountryRegionRepositoryTest {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RegionRepository regionRepository;

    Country testCountry;
    Region testRegion;

    @BeforeEach
    void setUp() {
        testCountry = countryRepository.findAll().getFirst();
        testRegion = regionRepository.findAll().getFirst();
    }

    @Test
    void CountryRegionTest() {
        System.out.println(countryRepository.count());
        System.out.println(regionRepository.count());
        System.out.println(testCountry.getName());
        System.out.println(testRegion.getName());
    }


}

