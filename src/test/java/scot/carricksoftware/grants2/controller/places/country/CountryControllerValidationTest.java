/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.country;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import scot.carricksoftware.grants2.controller.places.CountryController;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.services.places.CountryService;
import scot.carricksoftware.grants2.services.places.CountryServiceImpl;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static scot.carricksoftware.grants2.controller.places.CountryController.COUNTRY_PATH;


@WebMvcTest(CountryController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class CountryControllerValidationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CountryService countryServiceMock;

    @SuppressWarnings("unused")
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @SuppressWarnings("unused")
    @Captor
    ArgumentCaptor<CountryDTO> countryArgumentCaptor;

    CountryService countryService;

    @SuppressWarnings("unused")
    CountryDTO testCountryDTO;

    @BeforeEach
    void setUp() {
        countryService = new CountryServiceImpl();
        testCountryDTO = countryService.listCountries().getFirst();
        uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        countryArgumentCaptor = ArgumentCaptor.forClass(CountryDTO.class);

    }

    @Test
    void createCountryNullNameTest() throws Exception {
        CountryDTO countryDTO = CountryDTO.builder()
                .name("")
                .build();

        given(countryServiceMock.saveNewCountry(any(CountryDTO.class)))
                .willReturn(countryService.listCountries().get(1));

        mockMvc.perform(post(COUNTRY_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryDTO)))
                .andExpect(status().isBadRequest());
    }


}