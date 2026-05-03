/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.region;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.places.RegionController;
import scot.carricksoftware.grants2.model.places.RegionDTO;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class RegionControllerListIT {
    @Autowired
    RegionController regionController;

    @Autowired
    RegionRepository regionRepository;


    @Test
    void listPeopleTest() {
        List<RegionDTO> dtoList = regionController.listRegions(null);
        assertThat(dtoList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void emptyListTest() {
        regionRepository.deleteAll();
        List<RegionDTO> dtoList = regionController.listRegions(null);
        assertThat(dtoList.size()).isEqualTo(0);
    }


}