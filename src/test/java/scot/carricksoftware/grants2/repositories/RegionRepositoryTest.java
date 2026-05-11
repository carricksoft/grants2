/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import scot.carricksoftware.grants2.bootstrap.BootstrapPlace;
import scot.carricksoftware.grants2.constants.ApplicationConstants;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@Import(BootstrapPlace.class)
class RegionRepositoryTest {

    @Autowired
    RegionRepository regionRepository;

    @Test
    void saveTest() {
        Region savedRegion = regionRepository.save(Region.builder()
                .name("Wales")
                .build());
        regionRepository.flush();

        assertThat(savedRegion).isNotNull();
        assertThat(savedRegion.getId()).isNotNull();
        assertThat(savedRegion.getName()).isEqualTo("Wales");
    }

    @SuppressWarnings("unused")
    @Test
    void nameTooLongTest() {
        String testString = longString(ApplicationConstants.NAME_FIELD_LENGTH + 1);
        assertThrows(ConstraintViolationException.class, ()-> {
            Region savedRegion = regionRepository.save(Region.builder()
                    .name(testString)
                    .build());
            regionRepository.flush();
        });
    }

    private String longString(@SuppressWarnings("SameParameterValue") int length) {
        StringBuilder output = new StringBuilder("1234567890");
        while (output.length() < length) {
            output.append(output);
        }
        return output.substring(0, length);
    }

    @Disabled
    @Test
    void getRegionByNameTest() {
        Page<Region> list = regionRepository.findAllByNameIsLikeIgnoreCase("%region 1%",null);
        assertThat(list.getContent().size()).isEqualTo(1);
    }


}

