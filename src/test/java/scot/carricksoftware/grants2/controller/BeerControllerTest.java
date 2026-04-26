package scot.carricksoftware.grants2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class BeerControllerTest {

    @Autowired
    PersonController personController;

    @Test
    void getBeerById() {

        System.out.println(personController.getPersonById(UUID.randomUUID()));

    }
}