/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.place;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scot.carricksoftware.grants2.model.places.PlaceDTO;


import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlaceServiceImplTest {

    private PlaceService placeService;
    private PlaceDTO placeDTO;

    @BeforeEach
    void setUp() {
        placeService = new PlaceServiceImpl();
        placeDTO = PlaceDTO.builder()
                .name("Test Name")
                .version(99)
                .build();
    }

    @Test
    void saveNewPlaceTest() {
        PlaceDTO savedDTO = placeService.saveNewPlace(placeDTO);
        assertThat(savedDTO.getId()).isNotNull();
        assertThat(savedDTO.getName()).isEqualTo("Test Name");
        assertThat(savedDTO.getVersion()).isEqualTo(99);
        assertThat(savedDTO.getCreatedDate().getDayOfYear())
                .isEqualTo(LocalDateTime.now().getDayOfYear());
        assertThat(savedDTO.getUpdatedDate().getDayOfYear())
                .isEqualTo(LocalDateTime.now().getDayOfYear());
    }

    @Test
    void getPlaceByIdTest() {
        PlaceDTO savedDTO = placeService.saveNewPlace(placeDTO);
        assertThat(placeService.getPlaceById(savedDTO.getId()).get()).isEqualTo(savedDTO);
    }

    @Test
    void deletePlaceByIdTest(){
        assertThat(placeService.deletePlaceById(UUID.randomUUID())).isTrue();
    }


    @Test
    void updatePlaceByIdTest(){
        PlaceDTO savedDTO = placeService.saveNewPlace(placeDTO);
        PlaceDTO updateDTO = PlaceDTO.builder()
                .name("New Name")
                .build();
        PlaceDTO updatedDTO= placeService.updatePlaceById(savedDTO.getId(), updateDTO).get();
        assertThat(updatedDTO.getName()).isEqualTo(updateDTO.getName());
        assertThat(updatedDTO.getCreatedDate()).isEqualTo(updateDTO.getCreatedDate());
        assertThat(updatedDTO.getUpdatedDate()).isNotEqualTo(updateDTO.getUpdatedDate());
    }
}