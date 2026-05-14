/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.place;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scot.carricksoftware.grants2.controller.rest.places.place.PlaceController;
import scot.carricksoftware.grants2.entities.places.Place;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.model.places.PlaceDTO;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SuppressWarnings("ClassHasNoToStringMethod")
@SpringBootTest
class PlaceControllerGetIT {
    @Autowired
    PlaceController placeController;

    @Autowired
    PlaceRepository placeRepository;


    @Test
    void getPlaceByIdTest() {
        Place place = placeRepository.findAll().get(0);
        PlaceDTO dto = placeController.getPlaceById(place.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void placeIsNotFoundTest(){
        assertThrows(NotFoundException.class, () -> placeController.getPlaceById(UUID.randomUUID()));
    }


}