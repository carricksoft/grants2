/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.bootstrap;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import scot.carricksoftware.grants2.entities.places.Country;
import scot.carricksoftware.grants2.entities.places.Place;
import scot.carricksoftware.grants2.entities.places.Region;
import scot.carricksoftware.grants2.repositories.places.CountryRepository;
import scot.carricksoftware.grants2.repositories.places.PlaceRepository;
import scot.carricksoftware.grants2.repositories.places.RegionRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BootstrapPlaces implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final PlaceRepository placeRepository;

    @Override
    public void run(@SuppressWarnings("NullableProblems") String... args) {
        loadCountryData();
        loadRegionData();
        loadPlaceData();
        if (countryRepository.count() <10 ) {
            loadCountryCSV();
        }
    }

    private void loadCountryCSV() {
        final String CsvFile = "csvdata/countries.csv";
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(CsvFile)).getPath());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (line != null) {
                    line = line.replace("\"","");
                    Country country = Country.builder()
                            .name(line.split(",")[1])
                            .version(1)
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build();
                    countryRepository.save(country);
                    countryRepository.flush();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void loadCountryData() {
        if (countryRepository.count() == 0) {
            Country country1 = Country.builder()
                    .name("Country 1")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Country country2 = Country.builder()
                    .name("Country 2")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Country country3 = Country.builder()
                    .name("Country 3")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();


            countryRepository.saveAll(Arrays.asList(country1, country2, country3));
        }

    }

    private void loadRegionData() {
        if (regionRepository.count() == 0) {
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

    private void loadPlaceData() {
        if (placeRepository.count() == 0) {
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
