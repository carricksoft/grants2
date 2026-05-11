/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import scot.carricksoftware.grants2.bootstrap.BootstrapPlace;
import scot.carricksoftware.grants2.constants.ApplicationConstants;
import scot.carricksoftware.grants2.entities.places.Place;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;



@DataJpaTest
@Import(BootstrapPlace.class)
class PlaceRepositoryTest {

    @Autowired
    PlaceRepository placeRepository;

    @Test
    void saveTest() {
        Place savedPlace = placeRepository.save(Place.builder()
                .name("Wales")
                .build());
        placeRepository.flush();

        assertThat(savedPlace).isNotNull();
        assertThat(savedPlace.getId()).isNotNull();
        assertThat(savedPlace.getName()).isEqualTo("Wales");
    }

    @SuppressWarnings("unused")
    @Test
    void nameTooLongTest() {
        String testString = longString(ApplicationConstants.NAME_FIELD_LENGTH + 1);
        assertThrows(ConstraintViolationException.class, ()-> {
            Place savedPlace = placeRepository.save(Place.builder()
                    .name(testString)
                    .build());
            placeRepository.flush();
        });
    }

    private String longString(@SuppressWarnings("SameParameterValue") int length) {
        StringBuilder output = new StringBuilder("1234567890");
        while (output.length() < length) {
            output.append(output);
        }
        return output.substring(0, length);
    }

    @Test
    void getPlaceByNameTest() {
        Page<Place> list = placeRepository.findAllByNameIsLikeIgnoreCase("%place 1%",null);
        assertThat(list.getContent().size()).isEqualTo(1);
    }


}

