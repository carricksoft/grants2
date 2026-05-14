/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.region;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.rest.places.region.RegionController;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.model.places.RegionDTO;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
class RegionControllerSaveIT {
    @Autowired
    RegionController regionController;

    @Autowired
    RegionRepository regionRepository;

    @Test
    @Transactional
    @Rollback
    void saveNewRegionTest() {
        RegionDTO regionDTO = RegionDTO.builder()
                .name("New Name")
                .build();

        @SuppressWarnings("rawtypes") ResponseEntity responseEntity = regionController.handlePost(regionDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        String[] locationUUID = Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[2]);

        Optional<Region> optionalRegion= regionRepository.findById(savedUUID);
        if (optionalRegion.isPresent()) {
            Region region = optionalRegion.get();
            assertThat(region).isNotNull();
        } else {
            fail();
        }
    }

}