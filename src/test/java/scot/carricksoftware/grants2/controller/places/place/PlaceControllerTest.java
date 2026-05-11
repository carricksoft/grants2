/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.place;

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
import scot.carricksoftware.grants2.controller.places.PlaceController;
import scot.carricksoftware.grants2.model.places.PlaceDTO;
import scot.carricksoftware.grants2.services.places.place.PlaceService;
import scot.carricksoftware.grants2.services.places.place.PlaceServiceImpl;
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
import static scot.carricksoftware.grants2.controller.places.PlaceController.PLACE_PATH;
import static scot.carricksoftware.grants2.controller.places.PlaceController.PLACE_PATH_ID;


@WebMvcTest(PlaceController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class PlaceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PlaceService placeServiceMock;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;


    PlaceService placeService;

    PlaceDTO testPlaceDTO;

    @BeforeEach
    void setUp() {
        placeService = new PlaceServiceImpl();
        testPlaceDTO = placeService.listPlaces(null,1,25)
                .getContent().get(0);
        uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
    }

    @Test
    void getPlaceByIdTest() throws Exception {
        given(placeServiceMock.getPlaceById(testPlaceDTO.getId())).willReturn(Optional.of(testPlaceDTO));

        mockMvc.perform(get(PLACE_PATH_ID, testPlaceDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testPlaceDTO.getId().toString())))
                .andExpect(jsonPath("$.name", is(testPlaceDTO.getName())));
    }

    @Test
    void listPeopleTest() throws Exception {
        given(placeServiceMock.listPlaces(any(),any(),any()))
                .willReturn(placeService.listPlaces(null,1,25));

        mockMvc.perform(get(PLACE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()",
                        is(placeService.listPlaces(null,1,25)
                                .getContent().size())));
    }

    @Test
    void getPlaceByIdNotFoundTest() throws Exception {

        given(placeServiceMock.getPlaceById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(PLACE_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void postNewPlaceTest() throws Exception {
        testPlaceDTO.setId(null);
        testPlaceDTO.setVersion(null);
        given(placeServiceMock.saveNewPlace(any(PlaceDTO.class)))
                .willReturn(placeService.listPlaces(null,1,25)
                        .getContent().get(1));

        mockMvc.perform(post(PLACE_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPlaceDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createNewPlaceValidationTest() throws Exception {
        PlaceDTO placeDTO = PlaceDTO.builder().build();
        given(placeServiceMock.saveNewPlace(any(PlaceDTO.class)))
                .willReturn(placeService.listPlaces(null,1,25)
                        .getContent().get(1));

        MvcResult mvcResult = mockMvc.perform(post(PlaceController.PLACE_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(placeDTO)))
                .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.length()", is(2)))
                                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void updatePlaceTest() throws Exception {
        PlaceDTO placeDTO = placeService.listPlaces(null,1,25)
                .getContent().get(0);
        given(placeServiceMock.updatePlaceById(any(), any())).willReturn(Optional.of(placeDTO));
        mockMvc.perform(put(PLACE_PATH_ID, testPlaceDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPlaceDTO)))
                .andExpect(status().isNoContent());

        verify(placeServiceMock).updatePlaceById(any(UUID.class), any(PlaceDTO.class));
    }

    @Test
    void deletePlaceTest() throws Exception {

        given(placeServiceMock.deletePlaceById(any())).willReturn(true);

        mockMvc.perform(delete(PLACE_PATH_ID, testPlaceDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(placeServiceMock).deletePlaceById(uuidArgumentCaptor.capture());
        assertThat(testPlaceDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }




}