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
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.mappers.places.PlaceMapper;
import scot.carricksoftware.grants2.model.places.PlaceDTO;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class PlaceControllerUpdateIT {
    @Autowired
    PlaceController placeController;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PlaceMapper placeMapper;


    @Test
    @Transactional
    @Rollback
    void updateExistingPlaceTest() {
        Place place = placeRepository.findAll().get(0);
        PlaceDTO placeDTO = placeMapper.placeToPlaceDto(place);
        placeDTO.setId(null);
        placeDTO.setVersion(null);
        final String name = "Martha";
        placeDTO.setName(name);

        @SuppressWarnings("rawtypes") ResponseEntity responseEntity = placeController.upDateById(place.getId(), placeDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Place updatedPlace = placeRepository.findAll().get(0);
        assertThat(updatedPlace.getName()).isEqualTo(name);
    }

    @Test
    @Transactional
    @Rollback
    void updateExistingPlaceNotFoundTest() {
        assertThrows(NotFoundException.class, () -> placeController.upDateById(UUID.randomUUID(), PlaceDTO.builder().build()));
    }



}