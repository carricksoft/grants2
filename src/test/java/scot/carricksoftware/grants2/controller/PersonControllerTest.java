package scot.carricksoftware.grants2.controller;

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

import scot.carricksoftware.grants2.model.PersonDTO;
import scot.carricksoftware.grants2.services.PersonService;
import scot.carricksoftware.grants2.services.PersonServiceImpl;
import tools.jackson.databind.ObjectMapper;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static scot.carricksoftware.grants2.controller.PersonController.PERSON_PATH;
import static scot.carricksoftware.grants2.controller.PersonController.PERSON_PATH_ID;


@WebMvcTest(PersonController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PersonService personServiceMock;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<PersonDTO> personArgumentCaptor;


    PersonService personService;

    PersonDTO testPersonDTO;

    @BeforeEach
    void setUp() {
        personService = new PersonServiceImpl();
        testPersonDTO = personService.listPeople().getFirst();
        uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        personArgumentCaptor = ArgumentCaptor.forClass(PersonDTO.class);

    }

    @Test
    void getPersonByIdTest() throws Exception {
        given(personServiceMock.getPersonById(testPersonDTO.getId())).willReturn(Optional.of(testPersonDTO));

        mockMvc.perform(get(PERSON_PATH_ID, testPersonDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testPersonDTO.getId().toString())))
                .andExpect(jsonPath("$.lastName", is(testPersonDTO.getLastName())));
    }

    @Test
    void listPeopleTest() throws Exception {
        given(personServiceMock.listPeople()).willReturn(personService.listPeople());

        mockMvc.perform(get(PERSON_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(personService.listPeople().size())));
    }

    @Test
    void getPersonByIdNotFoundTest() throws Exception {

        given(personServiceMock.getPersonById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(PERSON_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void postNewPersonTest() throws Exception {
        testPersonDTO.setId(null);
        testPersonDTO.setVersion(null);
        given(personServiceMock.saveNewPerson(any(PersonDTO.class))).willReturn(personService.listPeople().get(1));

        mockMvc.perform(post(PERSON_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPersonDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void putPersonTest() throws Exception {
        mockMvc.perform(put(PERSON_PATH_ID, testPersonDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPersonDTO)))
                .andExpect(status().isNoContent());

        verify(personServiceMock).updatePersonById(any(UUID.class), any(PersonDTO.class));
    }

    @Test
    void deletePersonTest() throws Exception {

        mockMvc.perform(delete(PERSON_PATH_ID, testPersonDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(personServiceMock).deletePersonById(uuidArgumentCaptor.capture());
        assertThat(testPersonDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void patchPersonTest() throws Exception {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("firstName", "New Name");

        mockMvc.perform(patch(PERSON_PATH_ID, testPersonDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personMap)))
                .andExpect(status().isNoContent());

        verify(personServiceMock).patchPersonById(uuidArgumentCaptor.capture(), personArgumentCaptor.capture());

        assertThat(testPersonDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(personMap.get("firstName")).isEqualTo(personArgumentCaptor.getValue().getFirstName());

    }


}