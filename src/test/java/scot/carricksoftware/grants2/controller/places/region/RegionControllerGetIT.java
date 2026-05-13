/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.region;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.places.RegionController;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.model.places.RegionDTO;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class RegionControllerGetIT {
    @Autowired
    RegionController regionController;

    @Autowired
    RegionRepository regionRepository;


    @Test
    @Transactional
    void getRegionByIdTest() {
        Region region = regionRepository.findAll().get(0);
        RegionDTO dto = regionController.getRegionById(region.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void regionIsNotFoundTest(){
        assertThrows(NotFoundException.class, () -> regionController.getRegionById(UUID.randomUUID()));
    }


}