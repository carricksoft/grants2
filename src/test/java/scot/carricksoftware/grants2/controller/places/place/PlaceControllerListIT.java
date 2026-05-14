/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.place;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.rest.places.place.PlaceController;
import scot.carricksoftware.grants2.model.places.PlaceDTO;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class PlaceControllerListIT {
    @Autowired
    PlaceController placeController;

    @Autowired
    PlaceRepository placeRepository;


    @Test
    void listPlacesTest() {
        Page<PlaceDTO> dtoList = placeController.listPlaces(null,1,25);
        assertThat(dtoList.getContent().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void emptyListTest() {
        placeRepository.deleteAll();
        Page<PlaceDTO> dtoList = placeController.listPlaces(null,1,25);
        assertThat(dtoList.getContent().size()).isEqualTo(0);
    }


}