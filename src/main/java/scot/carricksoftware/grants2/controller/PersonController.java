/*
 * Copyright (c) 2026.  Andrew Grant, Carrick Software. All rights reserved
 */

package scot.carricksoftware.grants2.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        log.debug("PersonService::listPeople");
        return personService.listPeople();
    }

    @SuppressWarnings("rawtypes")
    @PutMapping("/{id}")
    public ResponseEntity upDateById(@PathVariable UUID id, @RequestBody Person person) {
        log.debug("PersonService::updateById");
        personService.updateById(id, person);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable UUID id){
        log.debug("PersonService::getPersonById");
        return personService.getPersonById(id);
    }

    @SuppressWarnings({"rawtypes", "unused"})
    @PostMapping
    public ResponseEntity handlePost(@RequestBody Person person) {
        log.debug("PersonService::handlePost");
        Person savedPerson = personService.saveNewPerson(person);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/person/" + person.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

}
