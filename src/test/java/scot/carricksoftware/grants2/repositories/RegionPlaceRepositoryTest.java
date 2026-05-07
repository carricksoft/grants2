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

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class RegionPlaceRepositoryTest {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    PlaceRepository placeRepository;

    Region testRegion;
    Place testPlace;

    @BeforeEach
    void setUp() {
        testRegion = regionRepository.findAll().getFirst();
        testPlace = placeRepository.findAll().getFirst();
    }

    @Test
    @Transactional
    @Rollback
    void RegionPlaceTest() {
        testPlace.setRegion(testRegion);
        placeRepository.flush();
        regionRepository.flush();

        assertThat(testPlace.getRegion().getName()).isEqualTo(testRegion.getName());
        assertThat(testRegion.getPlaces().contains(testPlace)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void CascadeChildDeleteTest() {
        long parentRepositorySize = regionRepository.count();
        long childRepositorySize = placeRepository.count();
        UUID childId = testPlace.getId();
        UUID parentId = testRegion.getId();
        testPlace.setRegion(testRegion);

        placeRepository.delete(testPlace);
        placeRepository.flush();
        regionRepository.flush();

        assertThat(regionRepository.count()).isEqualTo(parentRepositorySize);
        assertThat(placeRepository.count()).isEqualTo(childRepositorySize - 1);
        assertThat(placeRepository.existsPlaceById(childId)).isFalse();
        assertThat(regionRepository.existsRegionById(parentId)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void CascadeParentDeleteTest() {
        long parentRepositorySize = regionRepository.count();
        long childRepositorySize = placeRepository.count();
        UUID parentId = testRegion.getId();
        testPlace.setRegion(testRegion);
        placeRepository.flush();
        regionRepository.flush();

        regionRepository.delete(testRegion);
        placeRepository.flush();
        regionRepository.flush();

        assertThat(regionRepository.existsRegionById(parentId)).isFalse();
        assertThat(regionRepository.count()).isEqualTo(parentRepositorySize - 1);
        assertThat(placeRepository.count()).isEqualTo(childRepositorySize - 1);
    }


}

