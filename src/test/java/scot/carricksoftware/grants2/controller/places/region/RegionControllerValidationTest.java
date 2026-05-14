/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.region;

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
import scot.carricksoftware.grants2.controller.rest.places.region.RegionController;
import scot.carricksoftware.grants2.model.places.RegionDTO;
import scot.carricksoftware.grants2.services.places.region.RegionService;
import scot.carricksoftware.grants2.services.places.region.RegionServiceImpl;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static scot.carricksoftware.grants2.controller.rest.places.region.RegionController.REGION_PATH;


@WebMvcTest(RegionController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class RegionControllerValidationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    RegionService regionServiceMock;

    @SuppressWarnings("unused")
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @SuppressWarnings("unused")
    @Captor
    ArgumentCaptor<RegionDTO> regionArgumentCaptor;

    private RegionService regionService;

    @SuppressWarnings("unused")
    private
    RegionDTO testRegionDTO;

    @BeforeEach
    void setUp() {
        regionService = new RegionServiceImpl();
        testRegionDTO = regionService.listRegions(null,1,25)
                .getContent().get(0);
        uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        regionArgumentCaptor = ArgumentCaptor.forClass(RegionDTO.class);

    }

    @Test
    void createRegionNullNameTest() throws Exception {
        RegionDTO regionDTO = RegionDTO.builder()
                .name("")
                .build();

        given(regionServiceMock.saveNewRegion(any(RegionDTO.class)))
                .willReturn(regionService.listRegions(null,1,25)
                        .getContent().get(1));

        mockMvc.perform(post(REGION_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(regionDTO)))
                .andExpect(status().isBadRequest());
    }


}