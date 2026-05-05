/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scot.carricksoftware.grants2.controller.PersonController;
import scot.carricksoftware.grants2.model.PersonDTO;
import scot.carricksoftware.grants2.repositories.PersonRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class PersonControllerListIT {
    @Autowired
    PersonController personController;

    @Autowired
    PersonRepository personRepository;


    @Test
    void listPeopleTest() {
        Page<PersonDTO> dtoList = personController
                .listPeople(null, null, 1, 25);
        assertThat(dtoList.getContent().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void emptyListTest() {
        personRepository.deleteAll();
        Page<PersonDTO> dtoList = personController
                .listPeople(null, null, 1, 25);
        assertThat(dtoList.getContent().size()).isEqualTo(0);
    }


}