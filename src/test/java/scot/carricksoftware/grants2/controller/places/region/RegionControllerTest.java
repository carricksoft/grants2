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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import scot.carricksoftware.grants2.controller.places.RegionController;
import scot.carricksoftware.grants2.model.places.RegionDTO;
import scot.carricksoftware.grants2.services.places.RegionService;
import scot.carricksoftware.grants2.services.places.RegionServiceImpl;
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
import static scot.carricksoftware.grants2.controller.places.RegionController.REGION_PATH;
import static scot.carricksoftware.grants2.controller.places.RegionController.REGION_PATH_ID;


@WebMvcTest(RegionController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class RegionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    RegionService regionServiceMock;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    RegionService regionService;

    RegionDTO testRegionDTO;

    @BeforeEach
    void setUp() {
        regionService = new RegionServiceImpl();
        testRegionDTO = regionService.listRegions().getFirst();
        uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
    }

    @Test
    void getRegionByIdTest() throws Exception {
        given(regionServiceMock.getRegionById(testRegionDTO.getId())).willReturn(Optional.of(testRegionDTO));

        mockMvc.perform(get(REGION_PATH_ID, testRegionDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testRegionDTO.getId().toString())))
                .andExpect(jsonPath("$.name", is(testRegionDTO.getName())));
    }

    @Test
    void listPeopleTest() throws Exception {
        given(regionServiceMock.listRegions()).willReturn(regionService.listRegions());

        mockMvc.perform(get(REGION_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(regionService.listRegions().size())));
    }

    @Test
    void getRegionByIdNotFoundTest() throws Exception {

        given(regionServiceMock.getRegionById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(REGION_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void postNewRegionTest() throws Exception {
        testRegionDTO.setId(null);
        testRegionDTO.setVersion(null);
        given(regionServiceMock.saveNewRegion(any(RegionDTO.class)))
                .willReturn(regionService.listRegions().get(1));

        mockMvc.perform(post(REGION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRegionDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void createNewRegionValidationTest() throws Exception {
        RegionDTO regionDTO = RegionDTO.builder().build();
        given(regionServiceMock.saveNewRegion(any(RegionDTO.class)))
                .willReturn(regionService.listRegions().get(1));

        MvcResult mvcResult = mockMvc.perform(post(RegionController.REGION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regionDTO)))
                .andExpect(status().isBadRequest())
                        .andExpect((ResultMatcher) jsonPath("$.length()", is(2)))
                                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void updateRegionTest() throws Exception {
        RegionDTO regionDTO = regionService.listRegions().getFirst();
        given(regionServiceMock.updateRegionById(any(), any())).willReturn(Optional.of(regionDTO));
        mockMvc.perform(put(REGION_PATH_ID, testRegionDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRegionDTO)))
                .andExpect(status().isNoContent());

        verify(regionServiceMock).updateRegionById(any(UUID.class), any(RegionDTO.class));
    }

    @Test
    void deleteRegionTest() throws Exception {

        given(regionServiceMock.deleteRegionById(any())).willReturn(true);

        mockMvc.perform(delete(REGION_PATH_ID, testRegionDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(regionServiceMock).deleteRegionById(uuidArgumentCaptor.capture());
        assertThat(testRegionDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }




}