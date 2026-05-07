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
import scot.carricksoftware.grants2.entities.places.Place;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class RegionPlaceRepositoryTest {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    PlaceRepository placeRepository;

    Region testRegion;
    Place testPlace;
    Place testPlace2;

    @BeforeEach
    void setUp() {
        testRegion = regionRepository.findAll().getFirst();
        testPlace = placeRepository.findAll().getFirst();
        testPlace2 = placeRepository.findAll().getLast();
    }

    @Test
    @Transactional
    @Rollback
    void RegionPlaceTest() {
       testPlace.setRegion(testRegion);
       testPlace2.setRegion(testRegion);

       regionRepository.flush();
       placeRepository.flush();
        assertThat(testPlace.getRegion().getName()).isEqualTo(testRegion.getName());
        assertThat(testPlace2.getRegion().getName()).isEqualTo(testRegion.getName());
        assertThat(testRegion.getPlaces().contains(testPlace)).isTrue();
        assertThat(testRegion.getPlaces().contains(testPlace2)).isTrue();
    }




}

