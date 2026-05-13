/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.bootstrap;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@SuppressWarnings("ClassHasNoToStringMethod")
@Component
@RequiredArgsConstructor
public class BootstrapRegion implements CommandLineRunner {

    private final RegionRepository regionRepository;

    @Override
    public void run(@SuppressWarnings("NullableProblems") String... args) {
        loadRegionData();
    }

    private void loadRegionData() {
        if (0 == regionRepository.count()) {
            Region region1 = Region.builder()
                    .name("Region 1")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Region region2 = Region.builder()
                    .name("Region 2")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Region region3 = Region.builder()
                    .name("Region 3")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();


            regionRepository.saveAll(Arrays.asList(region1, region2, region3));
        }
    }

}
