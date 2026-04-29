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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static scot.carricksoftware.grants2.controller.PersonController.PERSON_PATH;


@WebMvcTest(PersonController.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class PersonControllerValidationTest {

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
    void createPersonNullFirstNameTest() throws Exception {
        PersonDTO personDTO = PersonDTO.builder()
                .lastName("Boo")
                .build();

        given(personServiceMock.saveNewPerson(any(PersonDTO.class)))
                .willReturn(personService.listPeople().get(1));

        mockMvc.perform(post(PERSON_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createPersonNullLLastNameTest() throws Exception {
        PersonDTO personDTO = PersonDTO.builder()
                .firstName("Boo")
                .build();

        given(personServiceMock.saveNewPerson(any(PersonDTO.class)))
                .willReturn(personService.listPeople().get(1));

        mockMvc.perform(post(PERSON_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isBadRequest());
    }



}