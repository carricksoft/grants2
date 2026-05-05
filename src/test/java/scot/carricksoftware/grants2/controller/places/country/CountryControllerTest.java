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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import scot.carricksoftware.grants2.controller.places.CountryController;
import scot.carricksoftware.grants2.model.places.CountryDTO;
import scot.carricksoftware.grants2.services.places.CountryService;
import scot.carricksoftware.grants2.services.places.CountryServiceImpl;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static scot.carricksoftware.grants2.controller.places.CountryController.COUNTRY_PATH;
import static scot.carricksoftware.grants2.controller.places.CountryController.COUNTRY_PATH_ID;


@WebMvcTest(CountryController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class CountryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CountryService countryServiceMock;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;


    CountryService countryService;

    CountryDTO testCountryDTO;

    @BeforeEach
    void setUp() {
        countryService = new CountryServiceImpl();
        testCountryDTO = countryService.listCountries(null, 1, 25).getContent().getFirst();
        uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
    }

    @Test
    void getCountryByIdTest() throws Exception {
        given(countryServiceMock.getCountryById(testCountryDTO.getId())).willReturn(Optional.of(testCountryDTO));

        mockMvc.perform(get(COUNTRY_PATH_ID, testCountryDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCountryDTO.getId().toString())))
                .andExpect(jsonPath("$.name", is(testCountryDTO.getName())));
    }

    @Test
    void listCountriesTest() throws Exception {
        given(countryServiceMock.listCountries(any(), any(), any())).willReturn(countryService.listCountries(null, 1, 25));

        mockMvc.perform(get(COUNTRY_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(countryService.listCountries(null, 1, 25).getContent().size())));
    }

    @Test
    void getCountryByIdNotFoundTest() throws Exception {

        given(countryServiceMock.getCountryById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(COUNTRY_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void postNewCountryTest() throws Exception {
        testCountryDTO.setId(null);
        testCountryDTO.setVersion(null);
        given(countryServiceMock.saveNewCountry(any(CountryDTO.class))).willReturn(countryService.listCountries(null, 1, 25).getContent().get(1));

        mockMvc.perform(post(COUNTRY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCountryDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createNewCountryValidationTest() throws Exception {
        CountryDTO countryDTO = CountryDTO.builder().build();
        given(countryServiceMock.saveNewCountry(any(CountryDTO.class)))
                .willReturn(countryService.listCountries(null, 1, 25).getContent().get(1));

        MvcResult mvcResult = mockMvc.perform(post(CountryController.COUNTRY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(countryDTO)))
                .andExpect(status().isBadRequest())
                        .andExpect((ResultMatcher) jsonPath("$.length()", is(2)))
                                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void updateCountryTest() throws Exception {
        CountryDTO countryDTO = countryService.listCountries(null, 1, 25).getContent().getFirst();
        given(countryServiceMock.updateCountryById(any(), any())).willReturn(Optional.of(countryDTO));
        mockMvc.perform(put(COUNTRY_PATH_ID, testCountryDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCountryDTO)))
                .andExpect(status().isNoContent());

        verify(countryServiceMock).updateCountryById(any(UUID.class), any(CountryDTO.class));
    }

    @Test
    void deleteCountryTest() throws Exception {

        given(countryServiceMock.deleteCountryById(any())).willReturn(true);

        mockMvc.perform(delete(COUNTRY_PATH_ID, testCountryDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(countryServiceMock).deleteCountryById(uuidArgumentCaptor.capture());
        assertThat(testCountryDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }




}