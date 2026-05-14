/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.bootstrap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("ClassHasNoToStringMethod")
@SpringBootTest
class BootstrapRegionTest {

    @Autowired
    RegionRepository regionRepositoryMock;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    BootstrapRegion bootstrapRegion;


    @Test
    @Transactional
    @Rollback
    void runTest(){
        regionRepository.deleteAll();
        assertThat(regionRepository.count()).isEqualTo(0);
        bootstrapRegion.run();
        assertThat(regionRepository.count()).isEqualTo(3);
    }

}