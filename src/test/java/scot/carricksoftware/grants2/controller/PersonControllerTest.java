package scot.carricksoftware.grants2.controller;

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

import scot.carricksoftware.grants2.model.Person;
import scot.carricksoftware.grants2.services.PersonService;
import scot.carricksoftware.grants2.services.PersonServiceImpl;
import tools.jackson.databind.ObjectMapper;


import java.util.HashMap;
import java.util.Map;
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


@WebMvcTest(PersonController.class)
@ExtendWith(MockitoExtension.class)
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
    ArgumentCaptor<Person> personArgumentCaptor;


    PersonService personService;

    Person testPerson;

    @BeforeEach
    void setUp() {
        personService = new PersonServiceImpl();
        testPerson = personService.listPeople().getFirst();
        uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        personArgumentCaptor = ArgumentCaptor.forClass(Person.class);

    }

    @Test
    void getPersonByIdTest() throws Exception {
        given(personServiceMock.getPersonById(testPerson.getId())).willReturn(testPerson);

        mockMvc.perform(get("/people/" + testPerson.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testPerson.getId().toString())))
                .andExpect(jsonPath("$.lastName", is(testPerson.getLastName())));
    }

    @Test
    void listPeopleTest() throws Exception {
        given(personServiceMock.listPeople()).willReturn(personService.listPeople());

        mockMvc.perform(get("/people")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(personService.listPeople().size())));
    }

    @Test
    void postNewPersonTest() throws Exception {
        testPerson.setId(null);
        testPerson.setVersion(null);
        given(personServiceMock.saveNewPerson(any(Person.class))).willReturn(personService.listPeople().get(1));

        mockMvc.perform(post("/people")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPerson)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void putPersonTest() throws Exception {
        mockMvc.perform(put("/people/" + testPerson.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPerson)))
                .andExpect(status().isNoContent());

        verify(personServiceMock).updateById(any(UUID.class), any(Person.class));
    }

    @Test
    void deletePersonTest() throws Exception {

        mockMvc.perform(delete("/people/" + testPerson.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(personServiceMock).deleteById(uuidArgumentCaptor.capture());
        assertThat(testPerson.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void patchPersonTest() throws Exception {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("firstName", "New Name");

        mockMvc.perform(patch("/people/" + testPerson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personMap)))
                .andExpect(status().isNoContent());

        verify(personServiceMock).patchById(uuidArgumentCaptor.capture(), personArgumentCaptor.capture());

        assertThat(testPerson.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(personMap.get("firstName")).isEqualTo(personArgumentCaptor.getValue().getFirstName());

    }


}