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
import scot.carricksoftware.grants2.controller.places.PlaceController;
import scot.carricksoftware.grants2.model.places.PlaceDTO;
import scot.carricksoftware.grants2.services.places.place.PlaceService;
import scot.carricksoftware.grants2.services.places.place.PlaceServiceImpl;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static scot.carricksoftware.grants2.controller.places.PlaceController.PLACE_PATH;


@WebMvcTest(PlaceController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class PlaceControllerValidationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PlaceService placeServiceMock;

    @SuppressWarnings("unused")
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @SuppressWarnings("unused")
    @Captor
    ArgumentCaptor<PlaceDTO> placeArgumentCaptor;

    private PlaceService placeService;

    @SuppressWarnings("unused")
    PlaceDTO testPlaceDTO;

    @BeforeEach
    void setUp() {
        placeService = new PlaceServiceImpl();
        testPlaceDTO = placeService.listPlaces(null,1,25)
                .getContent().get(0);
        uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        placeArgumentCaptor = ArgumentCaptor.forClass(PlaceDTO.class);

    }

    @Test
    void createPlaceNullNameTest() throws Exception {
        PlaceDTO placeDTO = PlaceDTO.builder()
                .name("")
                .build();

        given(placeServiceMock.saveNewPlace(any(PlaceDTO.class)))
                .willReturn(placeService.listPlaces(null,1,25)
                        .getContent().get(1));

        mockMvc.perform(post(PLACE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeDTO)))
                .andExpect(status().isBadRequest());
    }


}