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
import scot.carricksoftware.grants2.controller.places.RegionController;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.exceptions.NotFoundException;
import scot.carricksoftware.grants2.mappers.places.RegionMapper;
import scot.carricksoftware.grants2.model.places.RegionDTO;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class RegionControllerUpdateIT {
    @Autowired
    RegionController regionController;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    RegionMapper regionMapper;


    @Test
    @Transactional
    @Rollback
    void updateExistingRegionTest() {
        Region region = regionRepository.findAll().getFirst();
        RegionDTO regionDTO = regionMapper.regionToRegionDto(region);
        regionDTO.setId(null);
        regionDTO.setVersion(null);
        final String name = "Martha";
        regionDTO.setName(name);

        @SuppressWarnings("rawtypes") ResponseEntity responseEntity = regionController.upDateById(region.getId(), regionDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Region updatedRegion = regionRepository.findAll().getFirst();
        assertThat(updatedRegion.getName()).isEqualTo(name);
    }

    @Test
    @Transactional
    @Rollback
    void updateExistingRegionNotFoundTest() {
        assertThrows(NotFoundException.class, () -> regionController.upDateById(UUID.randomUUID(), RegionDTO.builder().build()));
    }



}