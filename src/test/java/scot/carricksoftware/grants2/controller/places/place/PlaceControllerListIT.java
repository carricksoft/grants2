/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.place;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.places.PlaceController;
import scot.carricksoftware.grants2.model.places.PlaceDTO;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class PlaceControllerListIT {
    @Autowired
    PlaceController placeController;

    @Autowired
    PlaceRepository placeRepository;


    @Test
    void listPeopleTest() {
        List<PlaceDTO> dtoList = placeController.listPlaces();
        assertThat(dtoList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void emptyListTest() {
        placeRepository.deleteAll();
        List<PlaceDTO> dtoList = placeController.listPlaces();
        assertThat(dtoList.size()).isEqualTo(0);
    }


}