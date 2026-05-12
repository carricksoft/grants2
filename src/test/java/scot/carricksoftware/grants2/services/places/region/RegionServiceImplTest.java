/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.region;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scot.carricksoftware.grants2.model.places.RegionDTO;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RegionServiceImplTest {

    RegionService regionService;

    @BeforeEach
    void setUp() {
        regionService = new RegionServiceImpl();
    }

    @Test
    void saveNewRegionTest() {
        RegionDTO regionDTO = RegionDTO.builder()
                .name("Test Name")
                .version(99)
                .build();

        RegionDTO savedDTO = regionService.saveNewRegion(regionDTO);
        assertThat(savedDTO.getId()).isNotNull();
        assertThat(savedDTO.getName()).isEqualTo("Test Name");
        assertThat(savedDTO.getVersion()).isEqualTo(99);
        assertThat(savedDTO.getCreatedDate().getDayOfYear())
                .isEqualTo(LocalDateTime.now().getDayOfYear());
        assertThat(savedDTO.getUpdatedDate().getDayOfYear())
                .isEqualTo(LocalDateTime.now().getDayOfYear());
    }
}