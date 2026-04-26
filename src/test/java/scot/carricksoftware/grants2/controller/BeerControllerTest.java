package scot.carricksoftware.grants2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import scot.carricksoftware.grants2.model.Person;
import scot.carricksoftware.grants2.services.PersonService;
import scot.carricksoftware.grants2.services.PersonServiceImpl;

import static org.hamcrest.core.Is.is;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PersonService personServiceMock;

    final PersonService personService = new PersonServiceImpl();

    @Test
    void getPersonByIdTest() throws Exception {
        Person testPerson = personService.listPeople().getFirst();
        given(personServiceMock.getPersonById(testPerson.getId())).willReturn(testPerson);

        mockMvc.perform(get("/people/" + testPerson.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is (testPerson.getId().toString())))
                .andExpect(jsonPath("$.lastName", is (testPerson.getLastName())));
    }

    @Test
    void listPeopleTest() throws Exception {
        given(personServiceMock.listPeople()).willReturn(personService.listPeople());

        mockMvc.perform(get("/people")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(personService.listPeople().size())));
    }
}