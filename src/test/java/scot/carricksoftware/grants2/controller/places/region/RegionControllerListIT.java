/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.region;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.places.RegionController;
import scot.carricksoftware.grants2.model.places.RegionDTO;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class RegionControllerListIT {
    @Autowired
    RegionController regionController;

    @Autowired
    RegionRepository regionRepository;


    @Test
    void listPeopleTest() {
        Page<RegionDTO> dtoList = regionController.listRegions(null,1,25);
        assertThat(dtoList.getContent().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void emptyListTest() {
        regionRepository.deleteAll();
        Page<RegionDTO> dtoList = regionController.listRegions(null,1,25);
        assertThat(dtoList.getContent().size()).isEqualTo(0);
    }


}