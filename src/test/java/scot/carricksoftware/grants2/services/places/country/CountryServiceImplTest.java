/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.services.places.country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.model.places.RegionDTO;


import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CountryServiceImplTest {

    CountryService countryService;
    CountryDTO countryDTO;

    @BeforeEach
    void setUp() {
        countryService = new CountryServiceImpl();
        countryDTO = CountryDTO.builder()
                .name("Test Name")
                .version(99)
                .build();
    }

    @Test
    void saveNewCountryTest() {
        CountryDTO savedDTO = countryService.saveNewCountry(countryDTO);
        assertThat(savedDTO.getId()).isNotNull();
        assertThat(savedDTO.getName()).isEqualTo("Test Name");
        assertThat(savedDTO.getVersion()).isEqualTo(99);
        assertThat(savedDTO.getCreatedDate().getDayOfYear())
                .isEqualTo(LocalDateTime.now().getDayOfYear());
        assertThat(savedDTO.getUpdatedDate().getDayOfYear())
                .isEqualTo(LocalDateTime.now().getDayOfYear());
    }

    @Test
    void getCountryByIdTest() {
        CountryDTO savedDTO = countryService.saveNewCountry(countryDTO);
        assertThat(countryService.getCountryById(savedDTO.getId()).get()).isEqualTo(savedDTO);
    }

    @Test
    void deleteCountryByIdTest(){
        assertThat(countryService.deleteCountryById(UUID.randomUUID())).isTrue();
    }


    @Test
    void updateCountryByIdTest(){
        CountryDTO savedDTO = countryService.saveNewCountry(countryDTO);
        CountryDTO updateDTO = CountryDTO.builder()
                .name("New Name")
                .build();
        CountryDTO updatedDTO= countryService.updateCountryById(savedDTO.getId(), updateDTO).get();
        assertThat(updatedDTO.getName()).isEqualTo(updateDTO.getName());
        assertThat(updatedDTO.getCreatedDate()).isEqualTo(updateDTO.getCreatedDate());
        assertThat(updatedDTO.getUpdatedDate()).isNotEqualTo(updateDTO.getUpdatedDate());
    }
}