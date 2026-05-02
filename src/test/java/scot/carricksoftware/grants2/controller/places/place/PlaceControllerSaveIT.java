/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.place;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.places.PlaceController;
import scot.carricksoftware.grants2.entities.places.Place;
import scot.carricksoftware.grants2.model.places.PlaceDTO;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
class PlaceControllerSaveIT {
    @Autowired
    PlaceController placeController;

    @Autowired
    PlaceRepository placeRepository;

    @Test
    @Transactional
    @Rollback
    void saveNewPlaceTest() {
        PlaceDTO placeDTO = PlaceDTO.builder()
                .name("New Name")
                .build();

        @SuppressWarnings("rawtypes") ResponseEntity responseEntity = placeController.handlePost(placeDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        String[] locationUUID = Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[2]);

        Optional<Place> optionalPlace= placeRepository.findById(savedUUID);
        if (optionalPlace.isPresent()) {
            Place place = optionalPlace.get();
            assertThat(place).isNotNull();
        } else {
            fail();
        }
    }

}