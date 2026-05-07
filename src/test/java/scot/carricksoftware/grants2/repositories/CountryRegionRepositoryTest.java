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

import java.util.UUID;

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

        testRegion.setCountry(testCountry);
        assertThat(testRegion.getCountry().getName()).isEqualTo(testCountry.getName());
        assertThat(testRegion2.getCountry().getName()).isEqualTo(testCountry.getName());
        assertThat(testCountry.getRegions().contains(testRegion)).isTrue();
        assertThat(testCountry.getRegions().contains(testRegion2)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void CascadeChildDeleteTest() {
        Long parentRepositorySize = countryRepository.count();
        Long childRepositorySize = regionRepository.count();
        UUID childId = testRegion.getId();
        UUID parentId = testCountry.getId();
        testRegion.setCountry(testCountry);

        regionRepository.delete(testRegion);
        regionRepository.flush();
        countryRepository.flush();

        assertThat(countryRepository.count()).isEqualTo(parentRepositorySize);
        assertThat(regionRepository.count()).isEqualTo(childRepositorySize -1);
        assertThat(regionRepository.existsRegionById(childId)).isFalse();
        assertThat(countryRepository.existsCountryById(parentId)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void CascadeParentDeleteTest() {
        Long parentRepositorySize = countryRepository.count();
        Long childRepositorySize = regionRepository.count();
        UUID childId = testRegion.getId();
        UUID parentId = testCountry.getId();
        testRegion.setCountry(testCountry);

  //      countryRepository.delete(testCountry);
        regionRepository.flush();
        countryRepository.flush();

      //  assertThat(countryRepository.existsCountryById(parentId)).isFalse();
      //  assertThat(countryRepository.count()).isEqualTo(parentRepositorySize - 1 );
      //  assertThat(regionRepository.count()).isEqualTo(childRepositorySize - 1 );
    }


}

