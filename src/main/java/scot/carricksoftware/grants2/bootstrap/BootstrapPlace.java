/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.bootstrap;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import scot.carricksoftware.grants2.entities.places.Place;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapPlace implements CommandLineRunner {


    private final PlaceRepository placeRepository;

    @Override
    public void run(@SuppressWarnings("NullableProblems") String... args) {
        loadPlaceData();
    }

    private void loadPlaceData() {
        if (0 == placeRepository.count()) {
            Place place1 = Place.builder()
                    .name("Place 1")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Place place2 = Place.builder()
                    .name("Place 2")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Place place3 = Place.builder()
                    .name("Place 3")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            placeRepository.saveAll(Arrays.asList(place1, place2, place3));
        }
    }


}
