/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.region;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scot.carricksoftware.grants2.model.places.RegionDTO;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegionServiceImplTest {

    @SuppressWarnings("FieldNotUsedInToString")
    RegionService regionService;

    @SuppressWarnings("FieldNotUsedInToString")
    RegionDTO regionDTO;

    @BeforeEach
    void setUp() {
        regionService = new RegionServiceImpl();
        regionDTO = RegionDTO.builder()
                .name("Test Name")
                .version(99)
                .build();
    }

    @Test
    void saveNewRegionTest() {
        RegionDTO savedDTO = regionService.saveNewRegion(regionDTO);
        assertThat(savedDTO.getId()).isNotNull();
        assertThat(savedDTO.getName()).isEqualTo("Test Name");
        assertThat(savedDTO.getVersion()).isEqualTo(99);
        assertThat(savedDTO.getCreatedDate().getDayOfYear())
                .isEqualTo(LocalDateTime.now().getDayOfYear());
        assertThat(savedDTO.getUpdatedDate().getDayOfYear())
                .isEqualTo(LocalDateTime.now().getDayOfYear());
    }

    @Test
    void getRegionByIdTest() {
        RegionDTO savedDTO = regionService.saveNewRegion(regionDTO);
        assertThat(regionService.getRegionById(savedDTO.getId()).get()).isEqualTo(savedDTO);
    }

    @Test
    void deleteRegionByIdTest(){
        assertThat(regionService.deleteRegionById(UUID.randomUUID())).isTrue();
    }


    @Test
    void updateRegionByIdTest(){
        RegionDTO savedDTO = regionService.saveNewRegion(regionDTO);
        RegionDTO updateDTO = RegionDTO.builder()
                .name("New Name")
                .build();
        RegionDTO updatedDTO= regionService.updateRegionById(savedDTO.getId(), updateDTO).get();
        assertThat(updatedDTO.getName()).isEqualTo(updateDTO.getName());
        assertThat(updatedDTO.getCreatedDate()).isEqualTo(updateDTO.getCreatedDate());
        assertThat(updatedDTO.getUpdatedDate()).isNotEqualTo(updateDTO.getUpdatedDate());
    }

}