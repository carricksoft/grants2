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
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("ClassHasNoToStringMethod")
@SpringBootTest
class BootstrapPlaceTest {

    @Autowired
    PlaceRepository placeRepositoryMock;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    BootstrapPlace bootstrapPlace;


    @Test
    @Transactional
    @Rollback
    void runTest(){
        placeRepository.deleteAll();
        assertThat(placeRepository.count()).isEqualTo(0);
        bootstrapPlace.run();
        assertThat(placeRepository.count()).isEqualTo(3);
    }

}