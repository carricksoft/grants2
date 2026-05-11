/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.places.country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import scot.carricksoftware.grants2.controller.places.CountryController;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class CountryControllerListParametersIT {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

  @Test
    void testListByName() throws Exception {
      mockMvc.perform(get(CountryController.COUNTRY_PATH)
                      .queryParam("name", "Country 1"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.content.size()", is(1)));
  }

    @SuppressWarnings("RedundantThrows")
    @Test
    @Disabled
    void listPage2Test() throws Exception {
        mockMvc.perform(get(CountryController.COUNTRY_PATH)
                        .queryParam("name", "%A%")
                        .queryParam("pageNumber", "2")
                        .queryParam("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(5)));
    }


}