/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.core.Is.is;
import org.springframework.web.context.WebApplicationContext;
import scot.carricksoftware.grants2.controller.PersonController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
class PersonControllerListParametersIT {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

  @Test
    void listByFirstNameTest() throws Exception {
      mockMvc.perform(get(PersonController.PERSON_PATH)
                      .queryParam("firstName", "Person 1F"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.size()", is(1)));
  }

    @Test
    void listByLastNameTest() throws Exception {
        mockMvc.perform(get(PersonController.PERSON_PATH)
                        .queryParam("lastName", "Person 2L"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void listByFirstAndLastNameTest() throws Exception {
        mockMvc.perform(get(PersonController.PERSON_PATH)
                        .queryParam("firstName", "Person 3F")
                        .queryParam("lastName", "Person 3L"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void listByFirstAndLastNamePage2Test() throws Exception {
        mockMvc.perform(get(PersonController.PERSON_PATH)
                        .queryParam("firstName", "Person 3F")
                        .queryParam("lastName", "Person 3L"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void listByImpossibleParametersTest() throws Exception {
        mockMvc.perform(get(PersonController.PERSON_PATH)
                        .queryParam("firstName", "Person 1F")
                        .queryParam("lastName", "Person 2L"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)));
    }
}