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
import scot.carricksoftware.grants2.repositories.PersonRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
class PersonControllerListParametersIT {
    @Autowired
    PersonController personController;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    WebApplicationContext wac;


    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

  @Test
    void testListByFirstName() throws Exception {
      mockMvc.perform(get(PersonController.PERSON_PATH)
                      .queryParam("firstName", "Person 1F"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.size()", is(1)));
  }

}