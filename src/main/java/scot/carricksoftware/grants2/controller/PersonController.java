/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import scot.carricksoftware.grants2.services.PersonService;
import scot.carricksoftware.grants2.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;

    @GetMapping()
    public List<Person> listPeople(){
        log.debug("PersonService::ListPeople");
        return personService.listPeople();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable UUID id){
        log.debug("PersonService::GetPersonById");
        return personService.getPersonById(id);
    }

}
